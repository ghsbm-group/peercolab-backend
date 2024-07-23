package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFolderResponse {
  private Long id;
  private String name;
}
