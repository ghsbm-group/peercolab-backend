package com.ghsbm.group.peer.colab.domain.chat.persistence;

import com.ghsbm.group.peer.colab.domain.chat.core.model.LatestPostedMessage;
import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing.ChatRepository;
import com.ghsbm.group.peer.colab.domain.chat.persistence.model.ChatEntitiesMapper;
import com.ghsbm.group.peer.colab.domain.chat.persistence.model.MessageEntity;
import com.ghsbm.group.peer.colab.domain.chat.persistence.repository.MessagePsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.classes.core.ports.outgoing.ClassRepository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.UserRepository;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Implementation of the message repository interface. Reads and persists data into a db. */
@Component
@NoArgsConstructor
@Setter
public class ChatRepositoryAdapter implements ChatRepository {

  private MessagePsqlDbRepository messagePsqlDbRepository;

  private UserRepository userRepository;
  private ClassRepository classRepository;
  private ChatEntitiesMapper chatEntitiesMapper;

  @Autowired
  public ChatRepositoryAdapter(
      MessagePsqlDbRepository messagePsqlDbRepository,
      ChatEntitiesMapper chatEntitiesMapper,
      UserRepository userRepository,
      ClassRepository classRepository) {
    this.messagePsqlDbRepository = messagePsqlDbRepository;
    this.chatEntitiesMapper = chatEntitiesMapper;
    this.userRepository = userRepository;
    this.classRepository = classRepository;
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
    String userLogin = SecurityUtils.getCurrentUserLogin().get();
    Long userId = userRepository.findOneByLogin(userLogin).get().getId();

    final var messageEntity =
        MessageEntity.builder()
            .content(message.getContent())
            .postDate(LocalDateTime.now())
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
  public LatestPostedMessage getLatestPostedMessage(Long messageBoardId) {
    List<Message> messages =
        chatEntitiesMapper.fromMessageEntities(
            messagePsqlDbRepository.findByMessageboardId(messageBoardId));
    Collections.sort(messages, Comparator.comparing(o -> o.getPostDate()));
    Message latestMessage = messages.get(messages.size() - 1);
    return LatestPostedMessage.builder()
        .lastMessagePostedTime(latestMessage.getPostDate())
        .username(userRepository.findById(latestMessage.getUserId()).get().getLogin())
        .messageBoard(classRepository.findFolderById(messageBoardId).getName())
        .build();
  }
}
