package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.UserDTO;
import lombok.Data;

/** Encapsulates posted message data. */
@Data
public class PostedMessageDTO {
  private MessageDTO messageDTO;
  private UserDTO userDTO;
}
