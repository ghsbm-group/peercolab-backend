package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateFolderRequest {
  @NotNull
  @Size(min = 1, max = 50)
  private String name;

  private Long parentId;
  @NotNull private Long classConfigurationId;
  private String description;
  @NotNull private Boolean isMessageBoard;
}
