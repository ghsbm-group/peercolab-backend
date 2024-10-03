package com.ghsbm.group.peer.colab.domain.chat.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserToAdminMessagesResponse {

  private Long id;
  private String email;
  private String subject;
  private String content;
}
