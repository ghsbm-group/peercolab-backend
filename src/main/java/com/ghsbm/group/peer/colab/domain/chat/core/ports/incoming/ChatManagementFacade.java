package com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming;

import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.*;

import com.ghsbm.group.peer.colab.domain.chat.core.model.*;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing.ChatRepository;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.ClassManagementService;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception.UserIsNotEnrolledInClassConfigurationException;
import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.UserManagementService;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.*;
import org.springframework.stereotype.Service;

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
    if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(ADMIN)
        && !classManagementService.userIsEnrolled(messageboardId)) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }
    List<Message> messages = chatRepository.findMessagesByMessageBoardId(messageboardId);
    List<PostedMessage> list = new ArrayList<PostedMessage>(messages.size());
    for (Message message : messages) {
      list.add(messageToPostedMessage(message));
    }
    // update the date when the user access the message board last time
    classManagementService.saveOrUpdateUserMessageboardAccess(messageboardId);
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
    if (!SecurityUtils.hasCurrentUserAnyOfAuthorities(ADMIN)
        && !classManagementService.userIsEnrolled(message.getMessageboardId())) {
      throw new UserIsNotEnrolledInClassConfigurationException();
    }
    return chatRepository.create(message);
  }

  /**
   * @inheritDoc
   */
  @Override
  public LatestPostedMessage retrieveLatestPostedMessage(List<Long> folderId) {
    return chatRepository.retrieveLatestPostedMessage(folderId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public PostLike likeAMessage(Long messageId) {

    Objects.requireNonNull(messageId);
    return chatRepository.likeAPost(messageId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Long countMessagesAfterDate(ZonedDateTime lastAccessDate) {
    return chatRepository.countMessagesAfterDate(lastAccessDate);
  }

  @Override
  public ContactUsMessages sendMessageToAdmin(ContactUsMessages contactUsMessages) {
    Objects.requireNonNull(contactUsMessages.getUserEmail());
    Objects.requireNonNull(contactUsMessages.getSubject());
    Objects.requireNonNull(contactUsMessages.getContent());
    return chatRepository.create(contactUsMessages);
  }

  @Override
  public List<ContactUsMessages> retrieveMessagesFromUsers() {
    return chatRepository.retrieveMessageFromUsers();
  }

  protected PostedMessage messageToPostedMessage(Message message) {
    if (message == null) {
      return null;
    }
    String currentLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));

    User user =
        userManagementService
            .findOneById(message.getUserId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException("User not found with ID: " + message.getUserId()));
    return PostedMessage.builder()
        .id(message.getId())
        .content(message.getContent())
        .userId(message.getUserId())
        .postDate(message.getPostDate())
        .login(user.getUserName())
        .numberOfLikes(chatRepository.numberOfLikesOnAMessage(message.getId()))
        .roleUser(getUserAuthority(user.getAuthorities()))
        .numberOfPostsUser(chatRepository.numberOfPostsByUser(message.getUserId()))
        .numberOfLikesUser(chatRepository.getTotalNumberOfLikesByUserId(message.getUserId()))
        .isLikedByCurrentUser(chatRepository.currentUserLikedThePost(message.getId(), currentLogin))
        .build();
  }

  protected String getUserAuthority(Set<Authority> authorities) {
    if (authorities.contains(new Authority(ADMIN))) return "ADMIN";
    else if (authorities.contains(new Authority(STUDENT_ADMIN))) return "STUDENT ADMIN";

    return "STUDENT";
  }
}
