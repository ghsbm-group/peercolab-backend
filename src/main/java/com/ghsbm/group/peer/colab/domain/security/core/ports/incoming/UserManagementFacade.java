package com.ghsbm.group.peer.colab.domain.security.core.ports.incoming;

import static com.ghsbm.group.peer.colab.domain.security.core.model.AuthProvider.local;
import static com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants.USER_MUST_BE_LOGGED_IN;

import com.ghsbm.group.peer.colab.application.config.Constants;
import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.AdminUserDTO;
import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.UserDTO;
import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.RequestAuthority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.core.model.UserAuthorityRequest;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.exception.EmailAlreadyUsedException;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.exception.InvalidPasswordException;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.exception.UsernameAlreadyUsedException;
import com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing.MailService;
import com.ghsbm.group.peer.colab.domain.security.core.ports.outgoing.UserManagementRepository;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.repository.UserRepository;
import com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants;
import com.ghsbm.group.peer.colab.infrastructure.RandomUtil;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service class for managing users. */
@Service
@Transactional
public class UserManagementFacade implements UserManagementService {

  private final Logger log = LoggerFactory.getLogger(UserManagementFacade.class);

  private final UserManagementRepository userManagementRepository;

  private final PasswordEncoder passwordEncoder;

  private final CacheManager cacheManager;

  private final MailService mailService;

  public UserManagementFacade(
      UserManagementRepository userManagementRepository,
      PasswordEncoder passwordEncoder,
      CacheManager cacheManager,
      MailService mailService) {
    this.userManagementRepository = userManagementRepository;
    this.passwordEncoder = passwordEncoder;
    this.cacheManager = cacheManager;
    this.mailService = mailService;
  }

  @Override
  public Optional<User> activateRegistration(String key) {
    Optional<User> toBeActivated =
        userManagementRepository
            .findByActivationKey(key)
            .map(
                user -> {
                  // activate given user for the registration key.
                  user.setActivated(true);
                  user.setActivationKey(null);
                  log.debug("Activated user: {}", user);
                  clearUserCaches(user);
                  return user;
                });
    toBeActivated.ifPresent(userManagementRepository::persist);
    return toBeActivated;
  }

  @Override
  public Optional<User> completePasswordReset(String newPassword, String key) {
    log.debug("Reset user password for reset key {}", key);

    Optional<User> toHavePasswordReset =
        userManagementRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
            .map(
                user -> {
                  user.setPassword(passwordEncoder.encode(newPassword));
                  user.setResetKey(null);
                  user.setResetDate(null);
                  this.clearUserCaches(user);
                  return user;
                });
    toHavePasswordReset.ifPresent(userManagementRepository::persist);
    return toHavePasswordReset;
  }

  @Override
  public void requestPasswordReset(String mail) {
    Optional<User> user =
        userManagementRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(User::isActivated)
            .map(
                u -> {
                  u.setResetKey(RandomUtil.generateResetKey());
                  u.setResetDate(Instant.now());
                  this.clearUserCaches(u);
                  return u;
                });

    user.ifPresent(
        u -> {
          userManagementRepository.persist(u);
          mailService.sendPasswordResetMail(u);
        });
  }

  @Override
  public void registerUser(User userDTO, String password, Boolean requestAuthority) {
    userManagementRepository
        .findOneByLogin(userDTO.getLogin().toLowerCase())
        .ifPresent(
            existingUser -> {
              boolean removed = removeNonActivatedUser(existingUser);
              if (!removed) {
                throw new UsernameAlreadyUsedException();
              }
            });
    userManagementRepository
        .findOneByEmailIgnoreCase(userDTO.getEmail())
        .ifPresent(
            existingUser -> {
              boolean removed = removeNonActivatedUser(existingUser);
              if (!removed) {
                throw new EmailAlreadyUsedException();
              }
            });
    User newUser = new User();
    String encryptedPassword = passwordEncoder.encode(password);
    newUser.setLogin(userDTO.getLogin().toLowerCase());
    // new user gets initially a generated password
    newUser.setPassword(encryptedPassword);
    newUser.setFirstName(userDTO.getFirstName());
    newUser.setLastName(userDTO.getLastName());
    if (userDTO.getEmail() != null) {
      newUser.setEmail(userDTO.getEmail().toLowerCase());
    }
    newUser.setImageUrl(userDTO.getImageUrl());
    newUser.setLangKey(userDTO.getLangKey());
    newUser.setProvider(local);
    // new user is not active
    newUser.setActivated(true);
    // new user gets registration key
    // newUser.setActivationKey(RandomUtil.generateActivationKey());
    Set<Authority> authorities = new HashSet<>();
    userManagementRepository.findAuthority(AuthoritiesConstants.USER).ifPresent(authorities::add);
    newUser.setAuthorities(authorities);
    userManagementRepository.persist(newUser);
    this.clearUserCaches(newUser);
    log.debug("Created Information for User: {}", newUser);

    if (requestAuthority.booleanValue())
      userManagementRepository.requestRole(
          userManagementRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).get().getId(),
          AuthoritiesConstants.STUDENT_ADMIN);

    // mailService.sendActivationEmail(newUser);
  }

  private boolean removeNonActivatedUser(User existingUser) {
    if (existingUser.isActivated()) {
      return false;
    }
    userManagementRepository.delete(existingUser);
    this.clearUserCaches(existingUser);
    return true;
  }

  @Override
  public User createUser(User userDTO) {
    if (userManagementRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
      throw new UsernameAlreadyUsedException();
    } else if (userManagementRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
      throw new EmailAlreadyUsedException();
    }

    User user = new User();
    user.setLogin(userDTO.getLogin().toLowerCase());
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    if (userDTO.getEmail() != null) {
      user.setEmail(userDTO.getEmail().toLowerCase());
    }
    user.setImageUrl(userDTO.getImageUrl());
    if (userDTO.getLangKey() == null) {
      user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
    } else {
      user.setLangKey(userDTO.getLangKey());
    }
    String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
    user.setPassword(encryptedPassword);
    user.setResetKey(RandomUtil.generateResetKey());
    user.setResetDate(Instant.now());
    user.setActivated(true);
    if (userDTO.getAuthorities() != null) {
      Set<Authority> authorities =
          userDTO.getAuthorities().stream()
              .map(Authority::getName)
              .map(userManagementRepository::findAuthority)
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Collectors.toSet());
      user.setAuthorities(authorities);
    }
    userManagementRepository.persist(user);
    this.clearUserCaches(user);

    mailService.sendCreationEmail(user);

    log.debug("Created Information for User: {}", user);
    return user;
  }

  /**
   * Update all information for a specific user, and return the modified user.
   *
   * @param userDTO user to update.
   * @return updated user.
   */
  @Override
  public Optional<User> updateUser(User userDTO) {
    Optional<User> existingUser =
        userManagementRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
    if (existingUser.isPresent() && (!existingUser.orElseThrow().getId().equals(userDTO.getId()))) {
      throw new com.ghsbm.group.peer.colab.domain.security.controller.errors
          .EmailAlreadyUsedException();
    }
    existingUser = userManagementRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
    if (existingUser.isPresent() && (!existingUser.orElseThrow().getId().equals(userDTO.getId()))) {
      throw new UsernameAlreadyUsedException();
    }

    return Optional.of(userManagementRepository.findUserById(userDTO.getId()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(
            user -> {
              this.clearUserCaches(user);
              user.setLogin(userDTO.getLogin().toLowerCase());
              user.setFirstName(userDTO.getFirstName());
              user.setLastName(userDTO.getLastName());
              if (userDTO.getEmail() != null) {
                user.setEmail(userDTO.getEmail().toLowerCase());
              }
              user.setImageUrl(userDTO.getImageUrl());
              user.setActivated(userDTO.isActivated());
              user.setLangKey(userDTO.getLangKey());
              user.setAuthorities(userDTO.getAuthorities());
              userManagementRepository.persist(user);
              this.clearUserCaches(user);
              log.debug("Changed Information for User: {}", user);
              return user;
            });
  }

  @Override
  public void deleteUser(String login) {
    userManagementRepository
        .findOneByLogin(login)
        .ifPresent(
            user -> {
              userManagementRepository.delete(user);
              this.clearUserCaches(user);
              log.debug("Deleted User: {}", user);
            });
  }

  /**
   * Update basic information (first name, last name, email, language) for the current user.
   *
   * @param firstName first name of user.
   * @param lastName last name of user.
   * @param email email id of user.
   * @param langKey language key.
   * @param imageUrl image URL of user.
   * @param userLogin
   */
  @Override
  public void updateUser(
      String firstName,
      String lastName,
      String email,
      String langKey,
      String imageUrl,
      String userLogin) {
    Optional<User> existingUser = userManagementRepository.findOneByEmailIgnoreCase(email);
    if (existingUser.isPresent()
        && (!existingUser.orElseThrow().getLogin().equalsIgnoreCase(userLogin))) {
      throw new com.ghsbm.group.peer.colab.domain.security.controller.errors
          .EmailAlreadyUsedException();
    }
    Optional<User> currentUser = userManagementRepository.findOneByLogin(userLogin);
    if (currentUser.isEmpty()) {
      throw new IllegalStateException("User could not be found");
    }
    SecurityUtils.getCurrentUserLogin()
        .flatMap(userManagementRepository::findOneByLogin)
        .ifPresent(
            user -> {
              user.setFirstName(firstName);
              user.setLastName(lastName);
              if (email != null) {
                user.setEmail(email.toLowerCase());
              }
              user.setLangKey(langKey);
              user.setImageUrl(imageUrl);
              userManagementRepository.persist(user);
              this.clearUserCaches(user);
              log.debug("Changed Information for User: {}", user);
            });
  }

  @Override
  @Transactional
  public void changePassword(String currentClearTextPassword, String newPassword) {
    SecurityUtils.getCurrentUserLogin()
        .flatMap(userManagementRepository::findOneByLogin)
        .ifPresent(
            user -> {
              String currentEncryptedPassword = user.getPassword();
              if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                throw new InvalidPasswordException();
              }
              String encryptedPassword = passwordEncoder.encode(newPassword);
              user.setPassword(encryptedPassword);
              userManagementRepository.persist(user);
              this.clearUserCaches(user);
              log.debug("Changed password for User: {}", user);
            });
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
    return userManagementRepository.findAllUsers(pageable).map(AdminUserDTO::new);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
    return userManagementRepository.findAllActivatedUsers(pageable).map(UserDTO::new);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> getUserWithAuthoritiesByLogin(String login) {
    return userManagementRepository.findUserWithAuthoritiesByLoginName(login);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> getUserWithAuthorities() {
    return SecurityUtils.getCurrentUserLogin()
        .flatMap(userManagementRepository::findUserWithAuthoritiesByLoginName);
  }

  /**
   * Not activated users should be automatically deleted after 3 days.
   *
   * <p>This is scheduled to get fired everyday, at 01:00 (am).
   */
  @Scheduled(cron = "0 0 1 * * ?")
  public void removeNotActivatedUsers() {
    userManagementRepository
        .findInactiveUsersOlderThan(Instant.now().minus(3, ChronoUnit.DAYS))
        .forEach(
            user -> {
              log.debug("Deleting not activated user {}", user.getLogin());
              userManagementRepository.delete(user);
              this.clearUserCaches(user);
            });
  }

  /**
   * Gets a list of all the authorities.
   *
   * @return a list of all the authorities.
   */
  @Override
  @Transactional(readOnly = true)
  public List<String> getAuthorities() {
    return userManagementRepository.findAllAuthorities().stream().map(Authority::getName).toList();
  }

  @Override
  public Optional<User> findOneById(Long id) {
    return userManagementRepository.findUserById(id);
  }

  /**
   * @inheritDoc
   */
  @Override
  public void requestAuthorityCurrentUser() {
    User currentUser =
        userManagementRepository
            .findOneByLogin(
                SecurityUtils.getCurrentUserLogin()
                    .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN)))
            .get();
    if (currentUser.getAuthorities().contains(new Authority(AuthoritiesConstants.STUDENT_ADMIN))) {
      throw new IllegalStateException("The user has this authority");
    }

    userManagementRepository.requestRole(currentUser.getId(), AuthoritiesConstants.STUDENT_ADMIN);
  }

  /**
   * @inheritDoc
   */
  @Override
  public List<UserAuthorityRequest> findAllAuthorityRequests() {
    return userRequestAuthoritiesFrom(userManagementRepository.findAllRequests());
  }

  /**
   * @inheritDoc
   */
  @Override
  public void approveAuthorityRequest(Long userId) {
    User user =
        userManagementRepository
            .findUserById(userId)
            .orElseThrow(
                () -> new IllegalStateException("User with id" + userId + " does not exist"));
    Set<Authority> authorities = user.getAuthorities();
    authorities.add(new Authority(AuthoritiesConstants.STUDENT_ADMIN));
    user.setAuthorities(authorities);
    User persistedUser = userManagementRepository.persist(user);
    userManagementRepository.deleteByUserId(userId);
    clearUserCaches(persistedUser);
  }

  /**
   * @inheritDoc
   */
  @Override
  public void deleteAccount() {

    String login =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new IllegalStateException(USER_MUST_BE_LOGGED_IN));
    User user =
        userManagementRepository
            .findOneByLogin(login)
            .orElseThrow(
                () -> new IllegalStateException("user with login " + login + " does not exists"));
    user.setLogin(AuthoritiesConstants.DELETED_USER_NAME + user.getId());
    user.setEmail(AuthoritiesConstants.DELETED_USER_NAME + user.getId() + "@localhost.com");
    user.setFirstName(AuthoritiesConstants.DELETED_USER_NAME);
    user.setLastName(AuthoritiesConstants.DELETED_USER_NAME);
    String encryptedPassword = passwordEncoder.encode("aNo!nnY@mou#sUn$iHu%b" + user.getId());
    user.setPassword(encryptedPassword);
    user.setActivated(false);
    updateUser(user);
  }

  private void clearUserCaches(User user) {
    Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE))
        .evict(user.getLogin());
    if (user.getEmail() != null) {
      Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE))
          .evict(user.getEmail());
    }
  }

  protected List<UserAuthorityRequest> userRequestAuthoritiesFrom(
      List<RequestAuthority> requestAuthorities) {
    if (requestAuthorities == null) {
      return Collections.emptyList();
    }
    List<UserAuthorityRequest> list =
        new ArrayList<UserAuthorityRequest>(requestAuthorities.size());
    for (RequestAuthority requestAuthority : requestAuthorities) {
      list.add(toUserRequestAuthority(requestAuthority));
    }
    return list;
  }

  protected UserAuthorityRequest toUserRequestAuthority(RequestAuthority requestAuthority) {
    if (requestAuthority == null) {
      return null;
    }
    User user =
        userManagementRepository
            .findUserById(requestAuthority.getUserId())
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        "User with id" + requestAuthority.getUserId() + " does not exist"));

    return UserAuthorityRequest.builder()
        .id(user.getId())
        .login(user.getLogin())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .build();
  }
}
