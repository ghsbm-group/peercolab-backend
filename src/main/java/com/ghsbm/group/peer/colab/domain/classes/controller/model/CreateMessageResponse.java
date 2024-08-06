package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateMessageResponse {
  private Long userId;
  private LocalDate postDate;
  private String content;
}
