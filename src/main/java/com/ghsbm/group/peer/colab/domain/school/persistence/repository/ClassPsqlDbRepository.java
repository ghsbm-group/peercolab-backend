package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.ClassConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassPsqlDbRepository extends JpaRepository<ClassConfigurationEntity, Long> {}
