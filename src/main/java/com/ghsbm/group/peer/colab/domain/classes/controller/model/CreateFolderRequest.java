package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateFolderRequest {
  private String name;
  private Long parentId;
  private Long classConfigurationId;
}
