package com.ghsbm.group.peer.colab.domain.security.controller;

import com.ghsbm.group.peer.colab.application.config.Constants;
import com.ghsbm.group.peer.colab.domain.security.controller.errors.EmailAlreadyUsedException;
import com.ghsbm.group.peer.colab.domain.security.controller.errors.LoginAlreadyUsedException;
import com.ghsbm.group.peer.colab.domain.security.controller.model.UserDtoMapper;
import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.AdminUserDTO;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import com.ghsbm.group.peer.colab.domain.security.core.ports.incoming.UserManagementFacade;
import com.ghsbm.group.peer.colab.domain.security.infrastructure.persistence.model.UserEntity;
import com.ghsbm.group.peer.colab.infrastructure.AuthoritiesConstants;
import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;
import com.ghsbm.group.peer.colab.infrastructure.util.HeaderUtil;
import com.ghsbm.group.peer.colab.infrastructure.util.PaginationUtil;
import com.ghsbm.group.peer.colab.infrastructure.util.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing users.
 *
 * <p>This class accesses the {@link UserEntity} entity, and needs to fetch its collection of
 * authorities.
 *
 * <p>For a normal use-case, it would be better to have an eager relationship between User and
 * Authority, and send everything to the client side: there would be no View Model and DTO, a lot
 * less code, and an outer-join which would be good for performance.
 *
 * <p>We use a View Model and a DTO for 3 reasons:
 *
 * <ul>
 *   <li>We want to keep a lazy association between the user and the authorities, because people
 *       will quite often do relationships with the user, and we don't want them to get the
 *       authorities all the time for nothing (for performance reasons). This is the #1 goal: we
 *       should not impact our users' application because of this use-case.
 *   <li>Not having an outer join causes n+1 requests to the database. This is not a real issue as
 *       we have by default a second-level cache. This means on the first HTTP call we do the n+1
 *       requests, but then all authorities come from the cache, so in fact it's much better than
 *       doing an outer join (which will get lots of data from the database, for each HTTP call).
 *   <li>As this manages users, for security reasons, we'd rather have a DTO layer.
 * </ul>
 *
 * <p>Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api/admin")
public class UserManagementController {

  private static final List<String> ALLOWED_ORDERED_PROPERTIES =
      Collections.unmodifiableList(
          Arrays.asList(
              "id",
              "login",
              "firstName",
              "lastName",
              "email",
              "activated",
              "langKey",
              "createdBy",
              "createdDate",
              "lastModifiedBy",
              "lastModifiedDate"));

  private final Logger log = LoggerFactory.getLogger(UserManagementController.class);

  @Value("${peer.clientApp.name}")
  private String applicationName;

  private final UserManagementFacade userManagementFacade;
  private final UserDtoMapper userDtoMapper;

  public UserManagementController(
      UserManagementFacade userManagementFacade, UserDtoMapper userDtoMapper) {
    this.userManagementFacade = userManagementFacade;
    this.userDtoMapper = userDtoMapper;
  }

  /**
   * {@code POST /admin/users} : Creates a new user.
   *
   * <p>Creates a new user if the login and email are not already used, and sends an mail with an
   * activation link. The user needs to be activated on creation.
   *
   * @param userDTO the user to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     user, or with status {@code 400 (Bad Request)} if the login or email is already in use.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login or email is already in
   *     use.
   */
  @PostMapping("/users")
  @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
  public ResponseEntity<AdminUserDTO> createUser(@Valid @RequestBody AdminUserDTO userDTO)
      throws URISyntaxException {
    log.debug("REST request to save User : {}", userDTO);

    if (userDTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new user cannot already have an ID", "userManagement", "idexists");
      // Lowercase the user login before comparing with database
    } else {
      User newUser = userManagementFacade.createUser(userDtoMapper.userDTOToUser(userDTO));
      return ResponseEntity.created(new URI("/api/admin/users/" + newUser.getLogin()))
          .headers(
              HeaderUtil.createAlert(applicationName, "userManagement.created", newUser.getLogin()))
          .body(new AdminUserDTO(newUser));
    }
  }

  /**
   * {@code PUT /admin/users} : Updates an existing User.
   *
   * @param userDTO the user to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
   * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already in use.
   * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already in use.
   */
  @PutMapping("/users")
  @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
  public ResponseEntity<AdminUserDTO> updateUser(@Valid @RequestBody AdminUserDTO userDTO) {
    log.debug("REST request to update User : {}", userDTO);
    Optional<AdminUserDTO> updatedUser =
        userManagementFacade
            .updateUser(userDtoMapper.userDTOToUser(userDTO))
            .map(AdminUserDTO::new);

    return ResponseUtil.wrapOrNotFound(
        updatedUser,
        HeaderUtil.createAlert(applicationName, "userManagement.updated", userDTO.getLogin()));
  }

  /**
   * {@code GET /admin/users} : get all users with all the details - calling this are only allowed
   * for the administrators.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
   */
  @GetMapping("/users")
  @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
  public ResponseEntity<List<AdminUserDTO>> getAllUsers(
      @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
    log.debug("REST request to get all User for an admin");
    if (!onlyContainsAllowedProperties(pageable)) {
      return ResponseEntity.badRequest().build();
    }

    final Page<AdminUserDTO> page = userManagementFacade.getAllManagedUsers(pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  private boolean onlyContainsAllowedProperties(Pageable pageable) {
    return pageable.getSort().stream()
        .map(Sort.Order::getProperty)
        .allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
  }

  /**
   * {@code GET /admin/users/:login} : get the "login" user.
   *
   * @param login the login of the user to find.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user,
   *     or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/users/{login}")
  @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
  public ResponseEntity<AdminUserDTO> getUser(
      @PathVariable("login") @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
    log.debug("REST request to get User : {}", login);
    return ResponseUtil.wrapOrNotFound(
        userManagementFacade.getUserWithAuthoritiesByLogin(login).map(AdminUserDTO::new));
  }

  /**
   * {@code DELETE /admin/users/:login} : delete the "login" User.
   *
   * @param login the login of the user to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/users/{login}")
  @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
  public ResponseEntity<Void> deleteUser(
      @PathVariable("login") @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
    log.debug("REST request to delete User: {}", login);
    userManagementFacade.deleteUser(login);
    return ResponseEntity.noContent()
        .headers(HeaderUtil.createAlert(applicationName, "userManagement.deleted", login))
        .build();
  }
}
