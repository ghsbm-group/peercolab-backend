package com.ghsbm.group.peer.colab.domain.chat.controller.errors;

import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;

public class UserNotAuthorizedForMessageActionException extends BadRequestAlertException {

  private static final long serialVersionUID = 1L;

  public UserNotAuthorizedForMessageActionException() {
    super(
        "The user does not have the necessary permission",
        "chatManagement",
        "userNotAuthorizedForMessageAction");
  }
}
