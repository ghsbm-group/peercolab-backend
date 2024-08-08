package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.ClassConfigurationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** JPA repository for {@link ClassConfigurationEntity} */
public interface ClassPsqlDbRepository extends JpaRepository<ClassConfigurationEntity, Long> {

  /**
   * retrieves a list of {@link ClassConfigurationEntity} belonging to a department.
   *
   * @param departmentId the department id for which the class configurations are retrieved.
   * @return a list of {@link ClassConfigurationEntity} based on the departmentId.
   */
  List<ClassConfigurationEntity> findByDepartmentId(Long departmentId);

  /**
   * checks if an entity of {@link ClassConfigurationEntity} already exists in a certain department.
   *
   * @param name the name of entity
   * @param startYear beginning year
   * @return if the entity exists or not
   */
  boolean existsByNameAndStartYear(String name, int startYear);

  Optional<ClassConfigurationEntity> findByEnrolmentKey(String enrolmentKey);

  /**
   * Retrieve enrolment key based on class configuration id
   *
   * @param classConfigurationId
   * @return
   */
  @Query("Select c.enrolmentKey from ClassConfigurationEntity c where c.id = :classConfigurationId")
  String findEnrolmentKeyById(Long classConfigurationId);
}
