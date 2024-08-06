package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateMessageRequest {
  @NotNull
  @Size(min = 1, max = 250)
  private String content;

  @NotNull private Long userId;
  @NotNull private Long messageboardId;
}
