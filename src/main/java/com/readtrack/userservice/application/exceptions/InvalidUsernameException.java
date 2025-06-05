package com.readtrack.userservice.application.exceptions;

public class InvalidUsernameException extends RuntimeException {
  public InvalidUsernameException(String message) {
    super(message);
  }
}
