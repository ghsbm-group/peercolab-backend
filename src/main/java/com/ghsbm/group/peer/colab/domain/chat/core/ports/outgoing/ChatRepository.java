package com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.chat.core.model.LatestPostedMessage;
import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;

import java.util.List;

/**
 * Interface contract between core business logic and the persistence layer.
 *
 * <p>Contains methods for persisting and reading data.
 */
public interface ChatRepository {

  /**
   * Retrieves all messages belonging to this messageboard.
   *
   * @param messageboardId The messageboard id for which the messages are returned.
   * @return a list of {@link Message} entities.
   */
  List<Message> findMessagesByMessageBoardId(Long messageboardId);

  /**
   * Persists a message to the db.
   *
   * @param message the message to be persisted
   * @return A {@link Message} object with its id set.
   */
  Message create(Message message);

  /**
   * Retrieves the latest posted message from multiple message boards.
   *
   * @param messageboardIds The message board ids from where the last posted message is extracted.
   * @return A {@link LatestPostedMessage} object.
   */
  LatestPostedMessage retrieveLatestPostedMessage(List<Long> messageboardIds);
}
