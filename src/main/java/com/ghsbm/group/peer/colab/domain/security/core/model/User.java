package com.ghsbm.group.peer.colab.domain.security.core.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class User {
  private Long id;
  private String login;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private boolean activated = false;
  private String langKey;
  private String imageUrl;
  private String activationKey;
  private String resetKey;
  private Instant resetDate = null;
  private Set<Authority> authorities = new HashSet<>();

  private String createdBy;
  private Instant createdDate = Instant.now();
  private String lastModifiedBy;
  private Instant lastModifiedDate = Instant.now();
}
