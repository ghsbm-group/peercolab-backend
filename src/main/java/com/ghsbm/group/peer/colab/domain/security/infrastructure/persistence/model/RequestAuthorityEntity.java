package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entity for the request authority table. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request_authority")
public class RequestAuthorityEntity {

  @EmbeddedId private RequestAuthorityId id;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @MapsId("authorityName")
  @JoinColumn(name = "authority_name")
  private AuthorityEntity authority;

  public RequestAuthorityEntity(UserEntity user, AuthorityEntity authority) {
    this.id = new RequestAuthorityId(user.getId(), authority.getName());
    this.user = user;
    this.authority = authority;
  }
}
