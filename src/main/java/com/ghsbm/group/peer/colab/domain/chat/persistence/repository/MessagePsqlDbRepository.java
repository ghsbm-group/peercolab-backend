package com.ghsbm.group.peer.colab.domain.chat.persistence.repository;

import com.ghsbm.group.peer.colab.domain.chat.persistence.model.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

/** JPA repository for {@link MessageEntity} */
public interface MessagePsqlDbRepository extends JpaRepository<MessageEntity, Long> {
  /**
   * Finds all the messages associated with a message board
   *
   * @param messageboardId the message board id for which the messages are retrieved.
   * @return a list of {@link MessageEntity}
   */
  List<MessageEntity> findByMessageboardId(Long messageboardId);

  /**
   * Retruieves the lastest posted message from message boards
   *
   * @param messageboardId the message board ids
   * @param pageable a {@link Pageable} to retrieve only the latest message, set the page size to 1
   * @return a {@link Page} containing the latest posted message.
   */
  Page<MessageEntity> findByMessageboardIdInOrderByPostDateDesc(
      List<Long> messageboardId, Pageable pageable);
}
