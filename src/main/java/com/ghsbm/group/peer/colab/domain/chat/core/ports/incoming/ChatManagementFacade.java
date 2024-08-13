package com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing.ChatRepository;
import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.model.PostedMessage;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.ClassManagementService;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.UserManagementService;
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
  private final ClassManagementService classManagementService;
  private final UserManagementService userManagementService;

  public ChatManagementFacade(
      ChatRepository chatRepository,
      ClassManagementService classManagementService,
      UserManagementService userManagementService) {
    this.chatRepository = chatRepository;
    this.classManagementService = classManagementService;
    this.userManagementService = userManagementService;
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<PostedMessage> retrieveMessagesByMessageboardId(Long messageboardId) {
    Objects.requireNonNull(messageboardId);
    if (!userManagementService.getAuthorities().contains(ADMIN)) {
      classManagementService.userIsEnrolled(messageboardId);
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
    classManagementService.userIsEnrolled(message.getMessageboardId());
    return chatRepository.create(message);
  }

  protected PostedMessage messageToPostedMessage(Message message) {
    if (message == null) {
      return null;
    }
    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    return PostedMessage.builder()
        .id(message.getId())
        .content(message.getContent())
        .userId(message.getUserId())
        .postDate(message.getPostDate())
        .login(userLogin)
        .build();
  }
}
