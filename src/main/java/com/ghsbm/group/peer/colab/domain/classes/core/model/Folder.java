package com.ghsbm.group.peer.colab.domain.classes.core.model;

import lombok.Builder;
import lombok.Data;

/** Encapsulates information about a specific folder/messageboard. */
@Data
@Builder
public class Folder {
  private Long id;
  private String name;
  private Long parentId;
  private Long classConfigurationId;
  private String description;
  private Boolean isMessageBoard;
}
