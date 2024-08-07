package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.FacultyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** JPA repository for {@link FacultyEntity} */
@Repository
public interface FacultyPsqlDbRepository extends JpaRepository<FacultyEntity, Long> {

  /**
   * retrieves a list of {@link FacultyEntity} belonging to a university.
   *
   * @param universityId the university id for which the faculties are retrieved.
   * @return a list of {@link FacultyEntity} based on the universityId.
   */
  List<FacultyEntity> findByUniversityId(Long universityId);
}
