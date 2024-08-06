package com.ghsbm.group.peer.colab.domain.classes.persistence.repository;

import com.ghsbm.group.peer.colab.domain.classes.persistence.model.EnrolmentEntity;
import com.ghsbm.group.peer.colab.domain.classes.persistence.model.EnrolmentId;
import org.springframework.data.jpa.repository.JpaRepository;

/** JPA repository for {@link EnrolmentEntity} */
public interface EnrolmentPsqlDbRepository extends JpaRepository<EnrolmentEntity, EnrolmentId> {}
