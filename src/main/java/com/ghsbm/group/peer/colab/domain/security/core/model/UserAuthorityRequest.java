package com.ghsbm.group.peer.colab.domain.security.core.model;

import lombok.Builder;
import lombok.Data;

/** Encapsulates information about the user that requested an authority. */
@Builder
@Data
public class UserAuthorityRequest {
  private Long id;
  private String login;
  private String firstName;
  private String lastName;
  private String email;
}
