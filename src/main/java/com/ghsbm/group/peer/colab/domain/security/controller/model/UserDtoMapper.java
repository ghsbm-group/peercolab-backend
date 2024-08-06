package com.ghsbm.group.peer.colab.domain.security.controller.model;

import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.AdminUserDTO;
import com.ghsbm.group.peer.colab.domain.security.controller.model.dto.UserDTO;
import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import java.util.*;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * <p>Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct support is
 * still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserDtoMapper {
  public User userDTOToUser(AdminUserDTO userDTO) {
    if (userDTO == null) {
      return null;
    } else {
      User user = new User();
      user.setId(userDTO.getId());
      user.setLogin(userDTO.getLogin());
      user.setFirstName(userDTO.getFirstName());
      user.setLastName(userDTO.getLastName());
      user.setEmail(userDTO.getEmail());
      user.setImageUrl(userDTO.getImageUrl());
      user.setActivated(userDTO.isActivated());
      user.setLangKey(userDTO.getLangKey());
      Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
      user.setAuthorities(authorities);
      return user;
    }
  }

  private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
    Set<Authority> authorities = new HashSet<>();

    if (authoritiesAsString != null) {
      authorities =
          authoritiesAsString.stream()
              .map(
                  string -> {
                    Authority auth = new Authority();
                    auth.setName(string);
                    return auth;
                  })
              .collect(Collectors.toSet());
    }

    return authorities;
  }

  @Named("id")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  public UserDTO toDtoId(User user) {
    if (user == null) {
      return null;
    }
    UserDTO userDto = new UserDTO();
    userDto.setId(user.getId());
    return userDto;
  }

  @Named("idSet")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  public Set<UserDTO> toDtoIdSet(Set<User> users) {
    if (users == null) {
      return Collections.emptySet();
    }

    Set<UserDTO> userSet = new HashSet<>();
    for (User User : users) {
      userSet.add(this.toDtoId(User));
    }

    return userSet;
  }

  @Named("login")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "login", source = "login")
  public UserDTO toDtoLogin(User user) {
    if (user == null) {
      return null;
    }
    UserDTO userDto = new UserDTO();
    userDto.setId(user.getId());
    userDto.setLogin(user.getLogin());
    return userDto;
  }

  @Named("loginSet")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "login", source = "login")
  public Set<UserDTO> toDtoLoginSet(Set<User> users) {
    if (users == null) {
      return Collections.emptySet();
    }

    Set<UserDTO> userSet = new HashSet<>();
    for (User User : users) {
      userSet.add(this.toDtoLogin(User));
    }

    return userSet;
  }

  public User from(RegisterUserRequest registerUserRequest) {
    return userDTOToUser(registerUserRequest);
  }
}
