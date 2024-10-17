package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import lombok.Data;

/** Encapsulates a user data who posted a message. */
@Data
public class PostedMessageUserDTO {
  private Long id;
  private String login;
  private String role;
  private Long numberOfPosts;
  private Long numberOfTotalLikes;
}
