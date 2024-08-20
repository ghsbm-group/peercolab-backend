package com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.chat.core.model.LatestPostedMessage;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing.ChatRepository;
import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.model.PostedMessage;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.ClassManagementService;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.UserIsNotEnrolledInClassConfigurationException;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.ADMIN;
import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.USER_MUST_BE_LOGGED_IN;

/** Service that contains the core business logic. */
@Service
public class ChatManagementFacade implements ChatManagementService {

  private final ChatRepository chatRepository;
  private final ClassManagementService classManagementService;

  public ChatManagementFacade(
      ChatRepository chatRepository, ClassManagementService classManagementService) {
    this.chatRepository = chatRepository;
    this.classManagementService = classManagementService;
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<PostedMessage> retrieveMessagesByMessageboardId(Long messageboardId) {
    Objects.requireNonNull(messageboardId);
    if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(ADMIN)
        && !classManagementService.userIsEnrolled(messageboardId)) {
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
    if (!classManagementService.userIsEnrolled(message.getMessageboardId())) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }
    return chatRepository.create(message);
  }

  /**
   * @inheritDoc
   */
  @Override
  public LatestPostedMessage getLatestPostedMessage(Long folderId) {
    List<Long> messageboardIds = classManagementService.getMessageBoardsIds(folderId);
    List<LatestPostedMessage> latestPostedMessages = new ArrayList<LatestPostedMessage>();
    for (Long messageboardId : messageboardIds) {
      latestPostedMessages.add(chatRepository.getLatestPostedMessage(messageboardId));
    }
    Collections.sort(latestPostedMessages, Comparator.comparing(o -> o.getLastMessagePostedTime()));

    return latestPostedMessages.get(latestPostedMessages.size() - 1);
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
