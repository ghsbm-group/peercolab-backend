package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import lombok.Data;

/** Encapsulates posted message data. */
@Data
public class PostedMessageDTO {
  private MessageDTO messageDTO;
  private PostedMessageUserDTO postedMessageUserDTO;
}
