package com.ghsbm.group.peer.colab.domain.school.persistence.repository;

import com.ghsbm.group.peer.colab.domain.school.persistence.model.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** JPA repository for {@link CountryEntity} */
@Repository
public interface CountryPsqlDbRepository extends JpaRepository<CountryEntity, Long> {}
