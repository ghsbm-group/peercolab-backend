package com.ghsbm.group.peer.colab.domain.chat.core.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

/** Encapsulates information about the last posted message */
@Data
@Builder
public class LatestPostedMessage {

  private String messageBoard;
  private String username;
  private ZonedDateTime lastMessagePostedTime;
}
