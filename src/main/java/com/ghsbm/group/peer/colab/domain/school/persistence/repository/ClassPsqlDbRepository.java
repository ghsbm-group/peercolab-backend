package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.ClassConfigurationEntity;
import com.ghsbm.group.peer.colab.domain.school.persistence.model.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassPsqlDbRepository extends JpaRepository<ClassConfigurationEntity, Long> {
  List<ClassConfigurationEntity> findByDepartmentId(Long departmentId);
}
