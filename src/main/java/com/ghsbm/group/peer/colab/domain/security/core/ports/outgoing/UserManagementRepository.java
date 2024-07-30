package com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import java.nio.channels.FileChannel;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.el.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManagementRepository {

  Optional<User> findByActivationKey(String key);

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
}
