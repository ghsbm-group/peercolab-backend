package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import lombok.Data;

@Data
public class RenameFolderRequest {
  private Long id;
  private String newName;
}
