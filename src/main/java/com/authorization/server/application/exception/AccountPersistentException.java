package com.authorization.server.application.exception;

public class AccountPersistentException extends RuntimeException {
    public AccountPersistentException(String message) {
        super(message);
    }
}
