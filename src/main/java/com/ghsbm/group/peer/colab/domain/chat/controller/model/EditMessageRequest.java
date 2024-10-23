package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditMessageRequest {

  @NotNull private Long messageId;

  @NotNull
  @Size(min = 1, max = 10000)
  private String content;
}
