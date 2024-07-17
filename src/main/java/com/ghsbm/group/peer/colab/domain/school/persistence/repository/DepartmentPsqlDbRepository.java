package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.DepartmentEntity;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentPsqlDbRepository extends JpaRepository<DepartmentEntity, Long> {

  List<DepartmentEntity> findByFacultyId(Long facultyId);
}
