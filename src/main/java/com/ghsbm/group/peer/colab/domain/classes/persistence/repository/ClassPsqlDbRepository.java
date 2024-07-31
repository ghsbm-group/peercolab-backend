package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.ClassConfigurationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

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
   * @param noOfStudyYears number of study years
   * @param departmentId the department id to which it belongs
   * @return if the entity exists or not
   */
  boolean existsByNameAndStartYearAndNoOfStudyYearsAndDepartmentId(
      String name, int startYear, int noOfStudyYears, Long departmentId);
}
