package com.authorization.server.application.exception;

public class AccountContraintViolationException extends RuntimeException {
    public AccountContraintViolationException(String message) {
        super(message);
    }
}
