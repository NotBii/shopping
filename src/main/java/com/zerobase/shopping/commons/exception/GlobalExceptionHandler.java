package com.zerobase.shopping.commons.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(RuntimeException.class)
  public String handleRuntimeException(final RuntimeException e) {
    log.error("handlerRuntimeException : {}", e.getMessage());
    return e.getMessage();
  }

}
