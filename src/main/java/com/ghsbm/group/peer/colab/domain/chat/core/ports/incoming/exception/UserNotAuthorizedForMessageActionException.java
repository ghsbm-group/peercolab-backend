package com.ghsbm.group.peer.colab.domain.chat.core.ports.incoming.exception;

public class UserNotAuthorizedForMessageActionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UserNotAuthorizedForMessageActionException() {
    super("The user does not have the necessary permission");
  }
}
