package com.ghsbm.group.peer.colab.domain.security.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.AdminUserDTO;
import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.UserDTO;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import java.util.List;
import java.util.Optional;

import com.ghsbm.group.peer.colab.domain.security.core.model.UserAuthorityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface UserManagementService {

  @Transactional
  Optional<User> activateRegistration(String key);

  @Transactional
  Optional<User> completePasswordReset(String newPassword, String key);

  @Transactional
  void requestPasswordReset(String mail);

  @Transactional
  void registerUser(User userDTO, String password, Boolean requestAuthority);

  @Transactional
  User createUser(User userDTO);

  @Transactional
  Optional<User> updateUser(User userDTO);

  @Transactional
  void deleteUser(String login);

  @Transactional
  void updateUser(
      String firstName,
      String lastName,
      String email,
      String langKey,
      String imageUrl,
      String userLogin);

  @Transactional
  void changePassword(String currentClearTextPassword, String newPassword);

  @Transactional(readOnly = true)
  Page<AdminUserDTO> getAllManagedUsers(Pageable pageable);

  @Transactional(readOnly = true)
  Page<UserDTO> getAllPublicUsers(Pageable pageable);

  @Transactional(readOnly = true)
  Optional<User> getUserWithAuthoritiesByLogin(String login);

  @Transactional(readOnly = true)
  Optional<User> getUserWithAuthorities();

  @Transactional(readOnly = true)
  List<String> getAuthorities();

  @Transactional
  Optional<User> findOneById(Long id);

  /** Used when the logged-in user requests a certain authority (more precisely, STUDENT_ADMIN) */
  @Transactional
  void requestAuthorityCurrentUser();

  /**
   * Finds all the authority requests made by the users
   *
   * @return A list of {@link UserAuthorityRequest} that encapsulates details about the users that
   *     requested for STUDENT ADMIN authority
   */
  @Transactional
  List<UserAuthorityRequest> findAllAuthorityRequests();

  /**
   * Approving the authority for becoming a STUDENT ADMIN made by a user
   *
   * @param userId the user id that requested the authority
   */
  @Transactional
  void approveAuthorityRequest(Long userId);

  /** "Delete" an account by a logged-in user by changing the credentials */
  @Transactional
  void deleteAccount();
}
