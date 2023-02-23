package com.reknik.hr.controller.advice;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.MissingResourceException;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(value = {IllegalArgumentException.class, MissingResourceException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage objectNotFoundException(RuntimeException ex, WebRequest webRequest) {
    return new ErrorMessage(ex.getMessage());
  }
}
