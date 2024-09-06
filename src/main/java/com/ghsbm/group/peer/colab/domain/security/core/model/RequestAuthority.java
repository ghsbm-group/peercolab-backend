package com.ghsbm.group.peer.colab.domain.security.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Encapsulates information about requesting an authority. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestAuthority {
  private Long userId;
  private String authorityName;
}
