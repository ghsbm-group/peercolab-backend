package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence;

import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing.UserManagementRepository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserMapper;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.UserRepository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.AuthorityRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserManagementRepositoryAdapter implements UserManagementRepository {

  private final Logger log = LoggerFactory.getLogger(UserManagementRepositoryAdapter.class);
  private final UserRepository userRepository;
  private final AuthorityRepository authorityRepository;

  private final UserMapper userMapper;

  public UserManagementRepositoryAdapter(UserRepository userRepository,
      AuthorityRepository authorityRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.authorityRepository = authorityRepository;
    this.userMapper = userMapper;
  }

  @Override
  public Optional<User> findByActivationKey(String key) {
    return userRepository.findOneByActivationKey(key).map(userMapper::fromEntity);
  }

  @Override
  public User persist(User u) {
    UserEntity entity = userMapper.fromDomain(u);
    return userMapper.fromEntity(userRepository.save(entity));
  }

  @Override
  public Optional<User> findOneByResetKey(String key) {
    return userRepository.findOneByResetKey(key).map(userMapper::fromEntity);
  }

  @Override
  public Optional<User> findOneByEmailIgnoreCase(String mail) {
    return userRepository.findOneByEmailIgnoreCase(mail).map(userMapper::fromEntity);
  }

  @Override
  public Optional<User> findOneByLogin(String lowerCase) {
    return userRepository.findOneByLogin(lowerCase).map(userMapper::fromEntity);
  }

  @Override
  public void delete(User existingUser) {
    userRepository.delete(userMapper.fromDomain(existingUser));
    userRepository.flush();
  }

  @Override
  public Optional<Authority> findAuthority(String authorityName) {
    return authorityRepository.findById(authorityName).map(userMapper::fromEntity);
  }

  @Override
  public Optional<User> findUserById(Long id) {
    return userRepository.findById(id).map(userMapper::fromEntity);
  }

  @Override
  public Page<User> findAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(userMapper::fromEntity);
  }

  @Override
  public Page<User> findAllActivatedUsers(Pageable pageable) {
    return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(userMapper::fromEntity);
  }

  @Override
  public Optional<User> findUserWithAuthoritiesByLoginName(String login) {
    return userRepository.findOneWithAuthoritiesByLogin(login).map(userMapper::fromEntity);
  }

  @Override
  public Iterable<User> findInactiveUsersOlderThan(Instant minus) {
    return userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(minus).stream().map(userMapper::fromEntity).toList();
  }

  @Override
  public List<Authority> findAllAuthorities() {
    return authorityRepository.findAll().stream().map(userMapper::fromEntity).toList();
  }
}
