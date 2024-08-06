package com.ghsbm.group.peer.colab.domain.security.controller.errors;

import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class EmailAlreadyUsedException extends BadRequestAlertException {

  private static final long serialVersionUID = 1L;

  public EmailAlreadyUsedException() {
    super("Email is already in use!", "userManagement", "emailexists");
  }
}
