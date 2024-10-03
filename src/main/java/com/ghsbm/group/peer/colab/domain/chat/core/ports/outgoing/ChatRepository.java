package com.ghsbm.group.peer.colab.domain.chat.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.chat.core.model.LatestPostedMessage;
import com.ghsbm.group.peer.colab.domain.chat.core.model.Message;
import com.ghsbm.group.peer.colab.domain.chat.core.model.PostLike;
import com.ghsbm.group.peer.colab.domain.chat.core.model.UserToAdminMessages;

import java.time.ZonedDateTime;
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

  /**
   * Persists the appreciation of a message made by a user
   *
   * @param messageId The message id.
   * @return A {@link PostLike} object.
   */
  PostLike likeAPost(Long messageId);

  /**
   * Return the number of likes of a message
   *
   * @param messageId The id of the message for which the number of likes is returned
   * @return A {@link Long} which represents the number of likes of the message
   */
  Long numberOfLikesOnAMessage(Long messageId);

  /**
   * Return the number of posts made by a user
   *
   * @param userId The id of the user for which the number of posts is returned
   * @return A {@link Long} which represents the number of posts made by a user.
   */
  Long numberOfPostsByUser(Long userId);

  /**
   * Returns the total number of likes received from all posts made by a user.
   *
   * @param userId The id of the user for which the number of likes is returned.
   * @return A {@link Long} which represents the total number of likes from all the posted messages
   *     made by a user.
   */
  Long getTotalNumberOfLikesByUserId(Long userId);

  /**
   * Returns if the current logged user like a specific message.
   *
   * @param messageId The id of the message for which is doing the checks if it was liked by the
   *     logged user.
   * @param login The username of the logged user.
   * @return If the user like the message.
   */
  boolean currentUserLikedThePost(Long messageId, String login);

  /**
   * Returns the number of posted messages after a specific date
   *
   * @param lastAccessDate the date after the messages are counting
   * @return the number of posted messages
   */
  Long countMessagesAfterDate(ZonedDateTime lastAccessDate);

  public UserToAdminMessages create(UserToAdminMessages userToAdminMessages);

  public List<UserToAdminMessages> retrieveMessageFromUsers();
}
