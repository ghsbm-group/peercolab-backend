package com.ghsbm.group.peer.colab.domain.chat.persistence.model;

import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** Entity chat for the post likes table. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_likes")
public class PostLikesEntity {

  @EmbeddedId private PostLikesId id;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @MapsId("messageId")
  @JoinColumn(name = "message_id")
  private MessageEntity message;

  public PostLikesEntity(UserEntity user, MessageEntity messageEntity) {
    this.id = new PostLikesId(user.getId(), messageEntity.getId());
    this.user = user;
    this.message = messageEntity;
  }
}
