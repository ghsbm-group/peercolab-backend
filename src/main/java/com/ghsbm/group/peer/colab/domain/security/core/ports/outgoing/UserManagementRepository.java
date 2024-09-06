package com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.RequestAuthority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.RequestAuthorityEntity;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface UserManagementRepository {

  Optional<User> findByActivationKey(String key);

  @Transactional
  User persist(User u);

  Optional<User> findOneByResetKey(String key);

  Optional<User> findOneByEmailIgnoreCase(String mail);

  Optional<User> findOneByLogin(String lowerCase);

  void delete(User existingUser);

  Optional<Authority> findAuthority(String authorityName);

  Optional<User> findUserById(Long id);

  Page<User> findAllUsers(Pageable pageable);

  Page<User> findAllActivatedUsers(Pageable pageable);

  Optional<User> findUserWithAuthoritiesByLoginName(String login);

  Iterable<User> findInactiveUsersOlderThan(Instant minus);

  List<Authority> findAllAuthorities();

  /**
   * Persists a {@link RequestAuthorityEntity} to the db
   *
   * <p>Request a specific authority by a user
   *
   * @param userId the user identifier that requested the autority
   * @param authorityName the requested authority
   * @return a {@link RequestAuthority} object
   */
  RequestAuthority requestRole(Long userId, String authorityName);

  /**
   * Retrieves all requests
   * @return a list of {@link RequestAuthority}
   */
  List<RequestAuthority> findAllRequests();

  /**
   * Delete a {@link RequestAuthorityEntity} when approving the authority
   *
   * @param userId the user identifier
   */
  void deleteByUserId(Long userId);
}
