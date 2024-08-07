package com.ghsbm.group.peer.colab.domain.classes.controller.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/** Encapsulates message data. */
@Data
public class MessageDTO {

  private Long id;
  private String content;
  private LocalDateTime postDate;
}
