package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception;

public class ClassConfigurationAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ClassConfigurationAlreadyExistsException() {
    super("Class already exists!");
  }
}
