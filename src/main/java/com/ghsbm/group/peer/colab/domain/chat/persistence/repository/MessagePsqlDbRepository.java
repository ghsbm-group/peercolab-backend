package com.ghsbm.group.peer.colab.domain.chat.persistence.repository;

import com.ghsbm.group.peer.colab.domain.chat.persistence.model.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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
   * Retrieves the lastest posted message from message boards
   *
   * @param messageboardId the message board ids
   * @return a {@link Page} containing the latest posted message.
   */
  Optional<MessageEntity> findFirstByMessageboardIdInOrderByPostDateDesc(List<Long> messageboardId);

  /**
   * Retrieves all the messages posted by a user
   *
   * @param userId the user id
   * @return the number of posted messages by a user
   */
  Long countByUserId(Long userId);

  /**
   * Retrieves the number of posted messages after a specific date
   *
   * @param date the date after the messages are counting
   * @return the number of messages after the specific date
   */
  Long countByPostDateAfter(ZonedDateTime date);
}
