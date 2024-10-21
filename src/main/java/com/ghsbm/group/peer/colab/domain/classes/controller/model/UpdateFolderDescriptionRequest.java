package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateFolderDescriptionRequest {
  @NotNull private Long id;

  @NotNull
  @Size(min = 1, max = 500)
  private String newDescription;
}
