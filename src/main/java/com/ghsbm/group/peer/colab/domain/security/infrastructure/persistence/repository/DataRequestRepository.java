package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository;

import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.DataRequestEntity;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.RequestAuthorityEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** JPA repository for {@link RequestAuthorityEntity} */
public interface DataRequestRepository extends JpaRepository<DataRequestEntity, Long> {

  Optional<DataRequestEntity> findFirstByUserIdOrderByRequestTime(Long id);
}
