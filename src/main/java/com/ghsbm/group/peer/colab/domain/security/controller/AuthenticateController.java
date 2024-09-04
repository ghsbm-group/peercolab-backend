package com.ghsbm.group.peer.colab.domain.security.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ghsbm.group.peer.colab.domain.security.controller.model.LoginRequest;
import com.ghsbm.group.peer.colab.infrastructure.util.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/** Controller to authenticate users. */
@RestController
@RequestMapping("/api")
public class AuthenticateController {

  private final Logger log = LoggerFactory.getLogger(AuthenticateController.class);

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final TokenProvider tokenProvider;

  public AuthenticateController(
      AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginRequest loginRequest) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(), loginRequest.getPassword());

    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.createToken(authentication, loginRequest.isRememberMe());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(jwt);
    return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
  }

  /**
   * {@code GET /authenticate} : check if the user is authenticated, and return its login.
   *
   * @param request the HTTP request.
   * @return the login if the user is authenticated.
   */
  @GetMapping(value = "/authenticate", produces = "text/plain")
  public String isAuthenticated(HttpServletRequest request) {
    log.debug("REST request to check if the current user is authenticated");
    return request.getRemoteUser();
  }

  /** Object to return as body in JWT Authentication. */
  static class JWTToken {

    private String idToken;

    JWTToken(String idToken) {
      this.idToken = idToken;
    }

    @JsonProperty("id_token")
    String getIdToken() {
      return idToken;
    }

    void setIdToken(String idToken) {
      this.idToken = idToken;
    }
  }
}
