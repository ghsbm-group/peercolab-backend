package com.ghsbm.group.peer.colab.domain.classes.controller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Encapsulates folder data. */
@Data
@AllArgsConstructor
public class FolderDTO {
  private Long id;
  private String name;
  private Boolean isMessageBoard;
}
