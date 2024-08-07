package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateMessageResponse {
  private Long userId;
  private LocalDateTime postDate;
  private String content;
}
