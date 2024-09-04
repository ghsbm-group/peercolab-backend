package com.ghsbm.group.peer.colab.application.config.oauth2.user;

import com.ghsbm.group.peer.colab.domain.security.core.model.Authority;
import com.ghsbm.group.peer.colab.domain.security.core.model.User;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
public class UserPrincipal implements OAuth2User, UserDetails {
  private Long id;
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public UserPrincipal(
      Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal create(User user) {

    return new UserPrincipal(
        user.getId(),
        user.getEmail(),
        user.getPassword(),
        user.getAuthorities().stream()
            .map(Authority::getName)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()));
  }

  public static UserPrincipal create(User user, Map<String, Object> attributes) {
    UserPrincipal userPrincipal = UserPrincipal.create(user);
    userPrincipal.setAttributes(attributes);
    return userPrincipal;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getName() {
    return String.valueOf(id);
  }
}
