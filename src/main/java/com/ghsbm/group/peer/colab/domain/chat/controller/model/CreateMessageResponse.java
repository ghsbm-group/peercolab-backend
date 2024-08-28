package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class CreateMessageResponse {
  private Long userId;
  private ZonedDateTime postDate;
  private String content;
}
