package com.ghsbm.group.peer.colab.domain.chat.persistence;

import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing.ChatRepository;
import com.ghsbm.group.peer.colab.domain.chat.persistence.model.ChatEntitiesMapper;
import com.ghsbm.group.peer.colab.domain.chat.persistence.model.MessageEntity;
import com.ghsbm.group.peer.colab.domain.chat.persistence.repository.MessagePsqlDbRepository;
import com.ghsbm.group.peer.colab.domain.classes.persistence.repository.FolderPsqlDbRespository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.UserRepository;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/** Implementation of the message repository interface. Reads and persists data into a db. */
@Component
@NoArgsConstructor
@Setter
public class ChatRepositoryAdapter implements ChatRepository {

  private MessagePsqlDbRepository messagePsqlDbRepository;

  private FolderPsqlDbRespository folderPsqlDbRespository;
  private UserRepository userRepository;

  private ChatEntitiesMapper chatEntitiesMapper;

  @Autowired
  public ChatRepositoryAdapter(
      MessagePsqlDbRepository messagePsqlDbRepository,
      ChatEntitiesMapper chatEntitiesMapper,
      FolderPsqlDbRespository folderPsqlDbRespository,
      UserRepository userRepository) {
    this.messagePsqlDbRepository = messagePsqlDbRepository;
    this.chatEntitiesMapper = chatEntitiesMapper;
    this.userRepository = userRepository;
    this.folderPsqlDbRespository = folderPsqlDbRespository;
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
    final var userEntity =
        userRepository
            .findOneByLogin(userLogin)
            .orElseThrow(
                () ->
                    new IllegalStateException("User with username" + userLogin + "does not exist"));

    final var messageBoardEntity =
        folderPsqlDbRespository.getReferenceById(message.getMessageboardId());
    final var messageEntity =
        MessageEntity.builder()
            .content(message.getContent())
            .postDate(LocalDateTime.now())
            .user(userEntity)
            .messageboard(messageBoardEntity)
            .build();
    final var savedMessage = messagePsqlDbRepository.save(messageEntity);
    return chatEntitiesMapper.messageFromEntity(savedMessage);
  }
}
