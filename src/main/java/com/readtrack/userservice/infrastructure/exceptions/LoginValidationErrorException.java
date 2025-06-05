package com.readtrack.userservice.infrastructure.exceptions;

public class LoginValidationErrorException extends RuntimeException {
  public LoginValidationErrorException(String message) {
    super(message);
  }
}
