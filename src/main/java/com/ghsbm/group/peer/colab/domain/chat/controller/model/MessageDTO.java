package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import lombok.Data;

import java.time.ZonedDateTime;

/** Encapsulates message data. */
@Data
public class MessageDTO {

  private Long id;
  private String content;
  private ZonedDateTime postDate;
  private Long numberOfLikes;
  private boolean isLikedByCurrentUser;
}
