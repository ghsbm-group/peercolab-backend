package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.ClassConfigurationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
  @Query("SELECT c.enrolmentKey FROM ClassConfigurationEntity c WHERE c.id = :classConfigurationId")
  Optional<String> findEnrolmentKeyById(Long classConfigurationId);

  @Query("UPDATE ClassConfigurationEntity c SET c.name = :name WHERE c.id = :classId")
  @Modifying()
  void updateNameById(@Param("classId") Long classId, @Param("name") String name);
}
