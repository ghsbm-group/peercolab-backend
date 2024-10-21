package com.ghsbm.group.peer.colab.domain.classes.controller.model.dto;

import lombok.Builder;
import lombok.Data;

/** Encapsulates folder data. */
@Data
@Builder
public class FolderDTO {
  private Long id;
  private String name;
  private Boolean isMessageBoard;
  private String description;
}
