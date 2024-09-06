package com.ghsbm.group.peer.colab.domain.classes.persistence.model;

import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/** Entity class for tracking a user when accessing a message board. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_messageboard_access")
public class UserMessageboardAccessEntity {

  @EmbeddedId private UserMessageBoardAccessId id;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @MapsId("messageboardId")
  @JoinColumn(name = "messageboard_id ")
  private FolderEntity messageboard;

  @Column(name = "last_access_date")
  private ZonedDateTime lastAccessDate;

  public UserMessageboardAccessEntity(
      UserEntity user, FolderEntity messageboard, ZonedDateTime lastAccessDate) {
    this.id = new UserMessageBoardAccessId(user.getId(), messageboard.getId());
    this.user = user;
    this.messageboard = messageboard;
    this.lastAccessDate = lastAccessDate;
  }
}
