package com.ghsbm.group.peer.colab.domain.classes.controller.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Builder
@Data
public class FolderInformationResponse {

  private Long posts;
  private Long topics;
  private String messageBoard;
  private String username;
  private ZonedDateTime lastMessagePostedTime;
}
