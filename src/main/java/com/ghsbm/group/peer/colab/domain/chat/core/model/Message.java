package com.ghsbm.group.peer.colab.domain.chat.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/** Encapsulates information about a message. */
@Data
@Builder
public class Message {

  private Long id;
  private String content;
  private LocalDateTime postDate;
  private Long userId;
  private Long messageboardId;
}
