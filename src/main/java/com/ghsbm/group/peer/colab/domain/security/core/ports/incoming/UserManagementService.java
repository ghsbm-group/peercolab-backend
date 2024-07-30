package com.ghsbm.group.peer.colab.domain.security.core.ports.incoming;

import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.AdminUserDTO;
import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.UserDTO;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import java.util.List;
import java.util.Optional;
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
  void registerUser(User userDTO, String password);

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
}
