package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassPsqlDbRepository extends JpaRepository<ClassEntity, Long> {}
