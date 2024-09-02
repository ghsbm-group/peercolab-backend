package com.ghsbm.group.peer.colab.domain.security.core.model;

import lombok.Builder;
import lombok.Data;

/** Encapsulates information about requesting an authority. */
@Data
@Builder
public class RequestAuthority {
  private Long userId;
  private String authorityName;
}
