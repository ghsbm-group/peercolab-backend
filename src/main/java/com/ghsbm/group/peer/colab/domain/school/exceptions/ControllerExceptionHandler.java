package com.ghsbm.group.peer.colab.domain.school.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ControllerExceptionHandler is responsible for handling exceptions across the whole application.
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handles {@link ApiExceptionResponse} exceptions.
   *
   * @param ex the ApiExceptionResponse exception to handle.
   * @return a {@link ResponseEntity} containing the error details and HTTP status.
   */
  @ExceptionHandler(value = ApiExceptionResponse.class)
  protected ResponseEntity<Object> handleApiExceptionResponse(ApiExceptionResponse ex) {
    HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
    return responseEntityBuilder(
        ApiExceptionResponse.builder()
            .errors(ex.getErrors())
            .status(status)
            .message(ex.getMessage())
            .build());
  }

  /**
   * Builds a {@link ResponseEntity} from the given {@link ApiExceptionResponse}.
   *
   * @param ex the ApiExceptionResponse containing error details.
   * @return a {@link ResponseEntity}*** containing the error details and HTTP status.
   */
  private ResponseEntity<Object> responseEntityBuilder(ApiExceptionResponse ex) {
    return new ResponseEntity<>(ex, ex.getStatus());
  }
}
