package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository;


import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link AuthorityEntity} entity.
 */
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {}
