package com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing.ChatRepository;
import com.ghsbm.group.peer.colab.domain.classes.core.model.Folder;
import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.model.PostedMessage;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.UserIsNotEnrolledInClassConfigurationException;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing.UserManagementRepository;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.ADMIN;
import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.USER_MUST_BE_LOGGED_IN;

/** Service that contains the core business logic. */
@Service
public class ChatManagementFacade implements ChatManagementService {

  private final ChatRepository chatRepository;
  private final ClassRepository classRepository;
  private final UserManagementRepository userManagementRepository;

  public ChatManagementFacade(
      ChatRepository chatRepository,
      ClassRepository classRepository,
      UserManagementRepository userManagementRepository) {
    this.chatRepository = chatRepository;
    this.classRepository = classRepository;
    this.userManagementRepository = userManagementRepository;
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<PostedMessage> retrieveMessagesByMessageboardId(Long messageboardId) {

    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    Folder folder = classRepository.findFolderById(messageboardId);

    if (!classRepository.isEnrolled(userLogin, folder.getClassConfigurationId())
        && !SecurityUtils.hasCurrentUserThisAuthority(ADMIN)) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }

    List<Message> messages = chatRepository.findMessagesByMessageBoardId(messageboardId);
    List<PostedMessage> list = new ArrayList<PostedMessage>(messages.size());
    for (Message message : messages) {
      list.add(messageToPostedMessage(message));
    }
    return list;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Message createMessage(Message message) {
    Objects.requireNonNull(message);
    Objects.requireNonNull(message.getContent());
    Objects.requireNonNull(message.getMessageboardId());
    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    Folder folder = classRepository.findFolderById(message.getMessageboardId());
    if (!classRepository.isEnrolled(userLogin, folder.getClassConfigurationId())) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }
    return chatRepository.create(message);
  }

  protected PostedMessage messageToPostedMessage(Message message) {
    if (message == null) {
      return null;
    }
    return PostedMessage.builder()
        .id(message.getId())
        .content(message.getContent())
        .userId(message.getUserId())
        .postDate(message.getPostDate())
        .login(userManagementRepository.findUserById(message.getUserId()).get().getLogin())
        .build();
  }
}
