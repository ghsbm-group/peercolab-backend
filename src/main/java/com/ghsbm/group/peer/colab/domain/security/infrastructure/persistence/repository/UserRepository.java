package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository;

import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the {@link UserEntity} entity. */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  String USERS_BY_LOGIN_CACHE = "usersByLogin";

  String USERS_BY_EMAIL_CACHE = "usersByEmail";

  Optional<UserEntity> findOneByActivationKey(String activationKey);

  List<UserEntity> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
      Instant dateTime);

  Optional<UserEntity> findOneByResetKey(String resetKey);

  Optional<UserEntity> findOneByEmailIgnoreCase(String email);

  Optional<UserEntity> findOneByLogin(String login);

  @EntityGraph(attributePaths = "authorities")
  @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
  Optional<UserEntity> findOneWithAuthoritiesByLogin(String login);

  @EntityGraph(attributePaths = "authorities")
  @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
  Optional<UserEntity> findOneWithAuthoritiesByEmailIgnoreCase(String email);

  Page<UserEntity> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
