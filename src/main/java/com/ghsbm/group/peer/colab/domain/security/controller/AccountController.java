package com.ghsbm.group.peer.colab.domain.security.controller;

import com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.ClassManagementFacade;
import com.ghsbm.group.peer.colab.domain.security.controller.errors.EmailAlreadyUsedException;
import com.ghsbm.group.peer.colab.domain.security.controller.errors.InvalidPasswordException;
import com.ghsbm.group.peer.colab.domain.security.controller.errors.LoginAlreadyUsedException;
import com.ghsbm.group.peer.colab.domain.security.controller.model.*;
import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.AdminUserDTO;
import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.PasswordChangeDTO;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.UserManagementService;
import com.ghsbm.group.peer.colab.infrastructure.SecurityUtils;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing the current user's account. */
@RestController
@RequestMapping("/api")
public class AccountController {

  private static class AccountResourceException extends RuntimeException {

    private AccountResourceException(String message) {
      super(message);
    }
  }

  private final Logger log = LoggerFactory.getLogger(AccountController.class);

  private final UserManagementService userManagementService;

  private final UserDtoMapper userDtoMapper;

  private final UserMapperController userMapper;

  private final ClassManagementFacade classManagementFacade;

  public AccountController(
      UserManagementService userManagementService,
      UserDtoMapper userDtoMapper,
      UserMapperController userMapper,
      ClassManagementFacade classManagementFacade) {
    this.userManagementService = userManagementService;
    this.userDtoMapper = userDtoMapper;
    this.userMapper = userMapper;
    this.classManagementFacade = classManagementFacade;
  }

  /**
   * {@code POST /register} : register the user.
   *
   * @param registerUserRequest the managed user View Model.
   * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
   * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
   * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
   */
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerAccount(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
    if (isPasswordLengthInvalid(registerUserRequest.getPassword())) {
      throw new InvalidPasswordException();
    }

    User user =
        userManagementService.registerUser(
            userDtoMapper.from(registerUserRequest),
            registerUserRequest.getPassword(),
            registerUserRequest.getRequestAuthority());
    if (StringUtils.isNotBlank(registerUserRequest.getEnrolmentKey())) {
      classManagementFacade.enrolStudent(registerUserRequest.getEnrolmentKey(), user);
    }
  }

  /**
   * {@code GET /activate} : activate the registered user.
   *
   * @param key the activation key.
   * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
   */
  @GetMapping("/activate")
  public void activateAccount(@RequestParam(value = "key") String key) {
    Optional<User> user = userManagementService.activateRegistration(key);
    if (user.isEmpty()) {
      throw new AccountResourceException("No user was found for this activation key");
    }
  }

  /**
   * {@code GET /account} : get the current user.
   *
   * @return the current user.
   * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
   */
  @GetMapping("/account")
  public AdminUserDTO getAccount() {
    return userManagementService
        .getUserWithAuthorities()
        .map(AdminUserDTO::new)
        .orElseThrow(() -> new AccountResourceException("User could not be found"));
  }

  /**
   * {@code POST /account} : update the current user information.
   *
   * @param userDTO the current user information.
   * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
   * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
   */
  @PostMapping("/account")
  public void saveAccount(@Valid @RequestBody AdminUserDTO userDTO) {
    String userLogin =
        SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
    userManagementService.updateUser(
        userDTO.getFirstName(),
        userDTO.getLastName(),
        userDTO.getEmail(),
        userDTO.getLangKey(),
        userDTO.getImageUrl(),
        userLogin);
  }

  /**
   * {@code POST /account/change-password} : changes the current user's password.
   *
   * @param passwordChangeDto current and new password.
   * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
   */
  @PostMapping(path = "/account/change-password")
  public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
    if (isPasswordLengthInvalid(passwordChangeDto.getNewPassword())) {
      throw new InvalidPasswordException();
    }
    userManagementService.changePassword(
        passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
  }

  /**
   * {@code POST /account/reset-password/init} : Send an email to reset the password of the user.
   *
   * @param mail the mail of the user.
   */
  @PostMapping(path = "/account/reset-password/init")
  public void requestPasswordReset(@RequestBody String mail) {
    userManagementService.requestPasswordReset(mail);
  }

  /**
   * {@code POST /account/reset-password/finish} : Finish to reset the password of the user.
   *
   * @param finishResetPasswordRequest the generated key and the new password.
   * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
   * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be
   *     reset.
   */
  @PostMapping(path = "/account/reset-password/finish")
  public void finishPasswordReset(
      @RequestBody FinishResetPasswordRequest finishResetPasswordRequest) {
    if (isPasswordLengthInvalid(finishResetPasswordRequest.getNewPassword())) {
      throw new InvalidPasswordException();
    }
    Optional<User> user =
        userManagementService.completePasswordReset(
            finishResetPasswordRequest.getNewPassword(), finishResetPasswordRequest.getKey());

    if (!user.isPresent()) {
      throw new AccountResourceException("No user was found for this reset key");
    }
  }

  /** Endpoint for requesting an authority (more precisely STUDENT_ADMIN) by the logged-in user. */
  @PostMapping("/request-authority")
  public void requestAuthorityCurrentUser() {
    userManagementService.requestAuthorityCurrentUser();
  }

  private static boolean isPasswordLengthInvalid(String password) {
    return (StringUtils.isEmpty(password)
        || password.length() < RegisterUserRequest.PASSWORD_MIN_LENGTH
        || password.length() > RegisterUserRequest.PASSWORD_MAX_LENGTH);
  }

  /**
   * Endpoint for display all authorities requests by users
   *
   * @return a list of {@link UserAuthorityRequestResponse} that encapsulates details about users
   *     that requested authority of STUDENT ADMIN
   */
  @GetMapping(("/all-authority-requests"))
  public ResponseEntity<List<UserAuthorityRequestResponse>> retrieveMessagesByMessageboardId() {
    return ResponseEntity.ok(
        userMapper.userAuthorityRequestResponsesFromUserAuthorityRequest(
            userManagementService.findAllAuthorityRequests()));
  }

  /**
   * Endpoint for approving by the ADMIN a request for becoming a STUDENT ADMIN send by the user
   *
   * @param userId the user identifier
   */
  @PostMapping("/approve-authority")
  public void approveAuthority(@Valid final Long userId) {
    userManagementService.approveAuthorityRequest(userId);
  }

  @PostMapping("/delete-account")
  public void deleteAccount() {
    userManagementService.deleteAccount();
  }

  @PostMapping("/request-data")
  public void requestData() {
    userManagementService.requestData();
  }

  @GetMapping("/already-requested-data")
  public boolean requestedData() {
    return userManagementService.requestedData();
  }
}
