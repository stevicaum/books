package org.books.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  protected ResponseEntity<Object> handleMissingResources(final ResourceNotFoundException ex) {
    log.error("Resource not found", ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested resource cannot be found, " + ex.getMessage());
  }

  @ExceptionHandler(FieldValidationException.class)
  protected ResponseEntity<Object> handleValidationException(final FieldValidationException ex) {
    log.error("Error during validation", ex);
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(AlreadyExistException.class)
  protected ResponseEntity<Object> alreadyExistException(final AlreadyExistException ex) {
    log.error("Error exists", ex);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler( {InvocationTargetException.class, IllegalArgumentException.class, ClassCastException.class,
      ConversionFailedException.class})
  @ResponseBody
  public ResponseEntity handleMiscFailures(Throwable t) {
    log.error("Error validate", t);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(t.getMessage());
  }

}
