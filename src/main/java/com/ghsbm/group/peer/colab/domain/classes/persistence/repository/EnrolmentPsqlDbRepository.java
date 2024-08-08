package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.EnrolmentEntity;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.EnrolmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** JPA repository for {@link EnrolmentEntity} */
public interface EnrolmentPsqlDbRepository extends JpaRepository<EnrolmentEntity, EnrolmentId> {
  /**
   * Checks if an entity of {@link EnrolmentEntity} exists with the specific parameters.
   *
   * @param userLogin the user name
   * @param classConfigurationId the class configuratio id
   * @return if the user is enrolled in a specific class configuration
   */
  @Query(
      "SELECT CASE WHEN COUNT(e)>0 THEN true ELSE false END "
          + "FROM EnrolmentEntity e "
          + "WHERE e.user.login = :user_login AND "
          + "e.classConfiguration.id = :class_configuration_id")
  boolean existsByUserNameAndClassConfigurationId(
      @Param("user_login") String userLogin, @Param("class_configuration_id") Long classConfigurationId);
}
