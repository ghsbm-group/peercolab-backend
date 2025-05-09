package com.ghsbm.group.peer.colab.domain.security.controller.errors;

import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class LoginAlreadyUsedException extends BadRequestAlertException {

  private static final long serialVersionUID = 1L;

  public LoginAlreadyUsedException() {
    super("Username already used!", "userManagement", "userexists");
  }
}
