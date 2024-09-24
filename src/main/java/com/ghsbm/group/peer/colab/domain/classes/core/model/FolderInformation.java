package com.ghsbm.group.peer.colab.domain.classes.core.model;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

/** Encapsulates information about a specific folder for navigate through folders */
@Data
@Builder
public class FolderInformation {
  private Long posts;
  private Long topics;
  private String messageBoard;
  private String username;
  private ZonedDateTime lastMessagePostedTime;
  private Long numberOfUnreadMessages;
  private boolean hasAnyMessageBoard;
}
