package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.DepartmentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** JPA repository for {@link DepartmentEntity} */
public interface DepartmentPsqlDbRepository extends JpaRepository<DepartmentEntity, Long> {
  /**
   * retrieves a list of {@link DepartmentEntity} belonging to a faculty.
   *
   * @param facultyId the faculty id for which the departments are retrieved.
   * @return a list of {@link DepartmentEntity} based on the facultyId.
   */
  List<DepartmentEntity> findByFacultyId(Long facultyId);
}
