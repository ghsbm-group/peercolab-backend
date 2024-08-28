package com.ghsbm.group.peer.colab.domain.chat.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

/** Encapsulates information about a posted message. */
@Data
@Builder
public class PostedMessage {
  private Long id;
  private String content;
  private ZonedDateTime postDate;
  private Long userId;
  private String login;
  private Long numberOfLikes;
  private Long numberOfLikesUser;
  private Long numberOfPostsUser;
  private String roleUser;
  private boolean isLikedByCurrentUser;
}
