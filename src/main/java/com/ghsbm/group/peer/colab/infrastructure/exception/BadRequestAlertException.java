package com.ghsbm.group.peer.colab.infrastructure.exception;

import com.ghsbm.group.peer.colab.infrastructure.exception.ProblemDetailWithCause.ProblemDetailWithCauseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class BadRequestAlertException extends ErrorResponseException {

  private static final long serialVersionUID = 1L;

  private final String entityName;

  private final String errorKey;

  public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
    super(
        HttpStatus.BAD_REQUEST,
        ProblemDetailWithCauseBuilder.instance()
            .withStatus(HttpStatus.BAD_REQUEST.value())
            .withTitle(defaultMessage)
            .withProperty("message", "error." + errorKey)
            .withProperty("params", entityName)
            .build(),
        null);
    this.entityName = entityName;
    this.errorKey = errorKey;
  }

  public String getEntityName() {
    return entityName;
  }

  public String getErrorKey() {
    return errorKey;
  }

  public ProblemDetailWithCause getProblemDetailWithCause() {
    return (ProblemDetailWithCause) this.getBody();
  }
}
