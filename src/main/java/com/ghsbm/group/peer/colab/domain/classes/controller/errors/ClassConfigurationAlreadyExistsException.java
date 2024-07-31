package com.ghsbm.group.peer.colab.domain.classes.controller.errors;

import com.ghsbm.group.peer.colab.infrastructure.exception.BadRequestAlertException;

public class ClassConfigurationAlreadyExistsException extends BadRequestAlertException {
  private static final long serialVersionUID = 1L;

  public ClassConfigurationAlreadyExistsException() {
    super(
        "Class Configuration already exists",
        "classConfigurationManagement",
        "classconfigurationexists");
  }
}
