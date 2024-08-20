package com.ghsbm.group.peer.colab.domain.classes.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/** Encapsulates information about a specific folder for navigate through folders */
@Data
@Builder
public class FolderInformation {
  private Long posts;
  private Long topics;
  private String messageBoard;
  private String username;
  private LocalDateTime lastMessagePostedTime;
}
