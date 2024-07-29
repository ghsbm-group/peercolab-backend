package com.ghsbm.group.peer.colab.domain.school.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * ApiExceptionResponse is a custom exception class that represents an API exception response.
 */
@Getter
@JsonIgnoreProperties(value = {"stackTrace", "suppressed", "cause", "localizedMessage"})
public class ApiExceptionResponse extends Exception {
  private final HttpStatus status;
  private final List<String> errors;

  /**
   * Constructs a new ApiExceptionResponse with the specified details.
   *
   * @param message the details message
   * @param status the HTTP ststus code
   * @param errors the list of error messages
   */
  @Builder
  public ApiExceptionResponse(String message, HttpStatus status, List<String> errors) {
    super(message);
    this.status = status;
    this.errors = errors;
  }
}
