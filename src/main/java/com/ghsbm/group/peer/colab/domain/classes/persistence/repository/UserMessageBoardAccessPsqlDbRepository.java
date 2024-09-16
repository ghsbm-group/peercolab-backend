package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.UserMessageboardAccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/** JPA repository for {@link UserMessageboardAccessEntity} */
public interface UserMessageBoardAccessPsqlDbRepository
    extends JpaRepository<UserMessageboardAccessEntity, Long> {
  /**
   * Retrieves an object {@link UserMessageboardAccessEntity} based on logged-in user and accessed
   * message board.
   *
   * @param login the username of the logged-in user
   * @param messageBoardId the identifier of the message board
   * @return an object {@link UserMessageboardAccessEntity}
   */
  UserMessageboardAccessEntity findByUser_LoginAndMessageboard_Id(
      String login, Long messageBoardId);
}
