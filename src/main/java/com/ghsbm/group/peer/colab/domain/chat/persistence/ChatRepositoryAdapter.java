package com.ghsbm.group.peer.colab.domain.chat.persistence;

import com.ghsbm.group.peer.colab.domain.chat.core.model.ContactUsMessages;
import com.ghsbm.group.peer.colab.domain.chat.core.model.LatestPostedMessage;
import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.model.PostLike;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing.ChatRepository;
import com.ghsbm.group.peer.colab.domain.chat.persistence.model.ChatEntitiesMapper;
import com.ghsbm.group.peer.colab.domain.chat.persistence.model.ContactUsMessagesEntity;
import com.ghsbm.group.peer.colab.domain.chat.persistence.model.MessageEntity;
import com.ghsbm.group.peer.colab.domain.chat.persistence.model.PostLikesEntity;
import com.ghsbm.group.peer.colab.domain.chat.persistence.repository.ContactUsMessagesRepository;
import com.ghsbm.group.peer.colab.domain.chat.persistence.repository.MessagePsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.chat.persistence.repository.PostLikesPsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.UserRepository;
import com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Implementation of the message repository interface. Reads and persists data into a db. */
@Component
@NoArgsConstructor
@Setter
public class ChatRepositoryAdapter implements ChatRepository {

  private MessagePsqlDbRepository messagePsqlDbRepository;
  private PostLikesPsqlDbRepository postLikesPsqlDbRepository;
  private UserRepository userRepository;
  private ClassRepository classRepository;
  private ChatEntitiesMapper chatEntitiesMapper;
  private ContactUsMessagesRepository userToAdminMessagesRepository;

  @Autowired
  public ChatRepositoryAdapter(
      MessagePsqlDbRepository messagePsqlDbRepository,
      PostLikesPsqlDbRepository postLikesPsqlDbRepository,
      ChatEntitiesMapper chatEntitiesMapper,
      UserRepository userRepository,
      ClassRepository classRepository,
      ContactUsMessagesRepository userToAdminMessagesRepository) {
    this.messagePsqlDbRepository = messagePsqlDbRepository;
    this.chatEntitiesMapper = chatEntitiesMapper;
    this.userRepository = userRepository;
    this.classRepository = classRepository;
    this.postLikesPsqlDbRepository = postLikesPsqlDbRepository;
    this.userToAdminMessagesRepository = userToAdminMessagesRepository;
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<Message> findMessagesByMessageBoardId(Long messageboardId) {

    return chatEntitiesMapper.fromMessageEntities(
        messagePsqlDbRepository.findByMessageboardId(messageboardId));
  }

  /**
   * @inheritDoc
   */
  @Override
  public Message create(Message message) {
    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(
                () -> new IllegalStateException(AuthoritiesConstants.USER_MUST_BE_LOGGED_IN));
    Long userId = userRepository.findOneByLogin(userLogin).get().getId();

    final var messageEntity =
        MessageEntity.builder()
            .content(message.getContent())
            .postDate(ZonedDateTime.now())
            .userId(userId)
            .messageboardId(message.getMessageboardId())
            .build();
    final var savedMessage = messagePsqlDbRepository.save(messageEntity);
    return chatEntitiesMapper.messageFromEntity(savedMessage);
  }

  /**
   * @inheritDoc
   */
  @Override
  public LatestPostedMessage retrieveLatestPostedMessage(List<Long> messageboardIds) {
    MessageEntity lastMessage =
        messagePsqlDbRepository
            .findFirstByMessageboardIdInOrderByPostDateDesc(messageboardIds)
            .orElse(null);
    if (lastMessage == null) {
      return null;
    }
    UserEntity user =
        userRepository
            .findById(lastMessage.getUserId())
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        "User with id " + lastMessage.getUserId() + " does not exists"));
    return LatestPostedMessage.builder()
        .messageBoard(classRepository.findFolderById(lastMessage.getMessageboardId()).getName())
        .username(user.getUserName())
        .lastMessagePostedTime(lastMessage.getPostDate())
        .build();
  }

  /**
   * @inheritDoc
   */
  @Override
  public PostLike likeAPost(Long messageId) {

    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(
                () -> new IllegalStateException(AuthoritiesConstants.USER_MUST_BE_LOGGED_IN));
    UserEntity userEntity =
        userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        "User with username " + userLogin + " does not exist"));
    MessageEntity messageEntity =
        messagePsqlDbRepository
            .findById(messageId)
            .orElseThrow(
                () -> new BadRequestAlertException("Invalid message", "chat", "invalid.message"));
    PostLikesEntity postLikesEntity = new PostLikesEntity(userEntity, messageEntity);

    postLikesPsqlDbRepository.save(postLikesEntity);

    return PostLike.builder().messageId(messageEntity.getId()).userId(userEntity.getId()).build();
  }

  /**
   * @inheritDoc
   */
  @Override
  public Long numberOfLikesOnAMessage(Long messageId) {
    return postLikesPsqlDbRepository.countByMessageId(messageId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Long numberOfPostsByUser(Long userId) {
    return messagePsqlDbRepository.countByUserId(userId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Long getTotalNumberOfLikesByUserId(Long userId) {
    return postLikesPsqlDbRepository.countTotalLikesByUserId(userId);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean currentUserLikedThePost(Long messageId, String login) {
    return postLikesPsqlDbRepository.existsByMessageIdAndUserLogin(messageId, login);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Long countMessagesAfterDate(ZonedDateTime lastAccessDate) {
    return messagePsqlDbRepository.countByPostDateAfter(lastAccessDate);
  }

  @Override
  public ContactUsMessages create(ContactUsMessages contactUsMessages) {

    final var userToAdminMessage =
        ContactUsMessagesEntity.builder()
            .userEmail(contactUsMessages.getUserEmail())
            .subject(contactUsMessages.getSubject())
            .content(contactUsMessages.getContent())
            .build();
    final var savedUserToAdminMessages = userToAdminMessagesRepository.save(userToAdminMessage);
    return chatEntitiesMapper.fromEntity(savedUserToAdminMessages);
  }

  @Override
  public List<ContactUsMessages> retrieveMessageFromUsers() {
    return chatEntitiesMapper.fromEntities(userToAdminMessagesRepository.findAll());
  }

  @Override
  public Long countAllMessagesByMessageBoardId(Long messageboardId) {
    return messagePsqlDbRepository.countByMessageboardId(messageboardId);
  }

  @Override
  public Message retrieveMessageById(Long messageId) {
    return chatEntitiesMapper.messageFromEntity(
        messagePsqlDbRepository.getReferenceById(messageId));
  }

  @Override
  public void deleteMessage(Long messageId) {
    postLikesPsqlDbRepository.deleteByMessageId(messageId);
    messagePsqlDbRepository.deleteById(messageId);
  }

  @Override
  public Message editMessage(Long messageId, String content) {
    MessageEntity editedMessage = messagePsqlDbRepository.getReferenceById(messageId);
    editedMessage.setContent(content);
    editedMessage.setEdited(true);
    messagePsqlDbRepository.save(editedMessage);
    return chatEntitiesMapper.messageFromEntity(editedMessage);
  }
}
