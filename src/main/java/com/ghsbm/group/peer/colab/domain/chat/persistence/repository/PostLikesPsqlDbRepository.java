package com.ghsbm.group.peer.colab.domain.chat.persistence.repository;

import com.ghsbm.group.peer.colab.domain.chat.persistence.model.PostLikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** JPA repository for {@link PostLikesEntity} */
public interface PostLikesPsqlDbRepository extends JpaRepository<PostLikesEntity, Long> {
  /**
   * Returns the number of likes of a message.
   *
   * @param messageId The message identifier.
   * @return The number of likes of a message.
   */
  Long countByMessageId(Long messageId);

  /**
   * Returns the total number of likes received from all posts made by a user.
   *
   * @param userId The user identifier.
   * @return The number total of likes.
   */
  @Query(
      "SELECT COUNT(postlike.id) "
          + "FROM PostLikesEntity postlike "
          + "JOIN postlike.message m "
          + "WHERE m.userId = :userId")
  Long countTotalLikesByUserId(@Param("userId") Long userId);

  /**
   * Return if the user like a post
   *
   * @param messageId The message identifier
   * @param login The username of the user
   * @return if the current user like the message
   */
  boolean existsByMessage_IdAndUser_Login(Long messageId, String login);
}
