package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FolderInformationResponse {

  private Long posts;
  private Long topics;
  private String messageBoard;
  private String username;
  private ZonedDateTime lastMessagePostedTime;
  private Long numberOfUnreadMessages;
  private boolean hasAnyMessageBoard;
}
