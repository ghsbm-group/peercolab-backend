package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** An authority (a security role) used by Spring Security. */
@Entity
@Table(name = "authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuthorityEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(max = 50)
  @Id
  @Column(length = 50)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AuthorityEntity)) {
      return false;
    }
    return Objects.equals(name, ((AuthorityEntity) o).name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "Authority{" + "name='" + name + '\'' + "}";
  }
}
