package com.ghsbm.group.peer.colab.domain.classes.controller.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FolderInfoDTO {
  @NotNull
  @Size(min = 1, max = 50)
  private String name;

  private Long parentId;

  @NotNull(message = "classConfigurationId is required")
  private Long classConfigurationId;

  private String description;
}
