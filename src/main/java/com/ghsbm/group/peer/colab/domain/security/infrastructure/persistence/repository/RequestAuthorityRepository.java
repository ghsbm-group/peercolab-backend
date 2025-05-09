package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository;

import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.RequestAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/** JPA repository for {@link RequestAuthorityEntity} */
public interface RequestAuthorityRepository extends JpaRepository<RequestAuthorityEntity, Long> {
  void deleteByUser_Id(Long userId);
}
