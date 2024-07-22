package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.DepartmentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentPsqlDbRepository extends JpaRepository<DepartmentEntity, Long> {

  List<DepartmentEntity> findByFacultyId(Long facultyId);
}
