package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.EnrolmentEntity;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.EnrolmentId;
import org.springframework.data.jpa.repository.JpaRepository;

/** JPA repository for {@link EnrolmentEntity} */
public interface EnrolmentPsqlDbRepository extends JpaRepository<EnrolmentEntity, EnrolmentId> {
  /**
   * Checks if an entity of {@link EnrolmentEntity} exists with the specific parameters.
   *
   * @param userId the user id
   * @param classConfigurationId the class configuratio id
   * @return if the user is enrolled in a specific class configuration
   */
  boolean existsByUserIdAndClassConfigurationId(Long userId, Long classConfigurationId);
}
