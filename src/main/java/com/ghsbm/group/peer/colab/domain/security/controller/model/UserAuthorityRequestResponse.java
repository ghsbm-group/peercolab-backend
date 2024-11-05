package com.ghsbm.group.peer.colab.domain.security.controller.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserAuthorityRequestResponse {

  private Long userId;
  private String login;
  private String firstName;
  private String lastName;
  private String email;
}
