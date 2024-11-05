package com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence;

import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.RequestAuthority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing.UserManagementRepository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.*;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
  private final RequestAuthorityRepository requestAuthorityRepository;
  private final DataRequestRepository dataRequestRepository;

  private final UserMapper userMapper;

  public UserManagementRepositoryAdapter(
      UserRepository userRepository,
      AuthorityRepository authorityRepository,
      RequestAuthorityRepository requestAuthorityRepository,
      DataRequestRepository dataRequestRepository,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.authorityRepository = authorityRepository;
    this.requestAuthorityRepository = requestAuthorityRepository;
    this.dataRequestRepository = dataRequestRepository;
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
    return userRepository
        .findAllByIdNotNullAndActivatedIsTrue(pageable)
        .map(userMapper::fromEntity);
  }

  @Override
  public Optional<User> findUserWithAuthoritiesByLoginName(String login) {
    return userRepository.findOneWithAuthoritiesByLogin(login).map(userMapper::fromEntity);
  }

  @Override
  public Iterable<User> findInactiveUsersOlderThan(Instant minus) {
    return userRepository
        .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(minus)
        .stream()
        .map(userMapper::fromEntity)
        .toList();
  }

  @Override
  public List<Authority> findAllAuthorities() {
    return authorityRepository.findAll().stream().map(userMapper::fromEntity).toList();
  }

  /**
   * @inheritDoc
   */
  @Override
  public RequestAuthority requestRole(Long userId, String authorityName) {
    UserEntity user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () -> new IllegalStateException("User with id" + userId + " does not exist"));
    AuthorityEntity authority =
        authorityRepository
            .findById(authorityName)
            .orElseThrow(
                () -> new IllegalStateException("Authority " + authorityName + " does not exist"));
    RequestAuthorityEntity requestAuthorityEntity = new RequestAuthorityEntity(user, authority);
    requestAuthorityRepository.save(requestAuthorityEntity);
    return RequestAuthority.builder().userId(userId).authorityName(authorityName).build();
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<RequestAuthority> findAllRequests() {
    return userMapper.fromEntities(requestAuthorityRepository.findAll());
  }

  /**
   * @inheritDoc
   */
  @Override
  public void deleteRequestByUserId(Long userId) {
    requestAuthorityRepository.deleteByUser_Id(userId);
  }

  @Override
  public void saveRequestForData(User user) {
    dataRequestRepository.save(
        DataRequestEntity.builder()
            .user(userMapper.fromDomain(user))
            .requestTime(LocalDateTime.now())
            .build());
  }

  @Override
  public boolean hasRequestedDate(User user) {
    return dataRequestRepository
        .findFirstByUserIdOrderByRequestTime(user.getId())
        .map(d -> isWithinLast48Hours(d.getRequestTime()))
        .orElse(false);
  }

  public boolean isWithinLast48Hours(LocalDateTime dateTime) {
    LocalDateTime now = LocalDateTime.now();
    long hoursBetween = ChronoUnit.HOURS.between(dateTime, now);
    return hoursBetween >= 0 && hoursBetween < 48;
  }
}
