package com.ghsbm.group.peer.colab.domain.classes.controller.errors;

import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class UserIsNotEnrolledInClassConfigurationException extends BadRequestAlertException {
  private static final long serialVersionUID = 1L;

  public UserIsNotEnrolledInClassConfigurationException() {
    super("The user is not enrolled in the class", "enrolManagement", "usernotenrolled");
  }
}
