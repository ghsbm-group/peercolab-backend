package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/** Encapsulates message data. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

  private Long id;
  private String content;
  private ZonedDateTime postDate;
  private Long numberOfLikes;
  private boolean isLikedByCurrentUser;
  private boolean isEdited;
}
