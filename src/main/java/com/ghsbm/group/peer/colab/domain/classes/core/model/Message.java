package com.ghsbm.group.peer.colab.domain.classes.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/** Encapsulates information about a message. */
@Data
@Builder
public class Message {

  private Long id;
  private String content;
  private LocalDate postDate;
  private Long userId;
  private Long messageboardId;
}
