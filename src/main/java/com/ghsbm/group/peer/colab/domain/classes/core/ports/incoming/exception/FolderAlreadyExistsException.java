package com.ghsbm.group.peer.colab.domain.classes.core.ports.incoming.exception;

public class FolderAlreadyExistsException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public FolderAlreadyExistsException() {
    super("Folder already exists!");
  }
}
