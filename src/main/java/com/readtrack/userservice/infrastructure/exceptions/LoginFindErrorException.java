package com.readtrack.userservice.infrastructure.exceptions;

public class LoginFindErrorException extends RuntimeException {
    public LoginFindErrorException(String message) {
        super(message);
    }
}
