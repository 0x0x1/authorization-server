package com.authorization.server.infrastructure.persistence;

import lombok.Getter;

/**
 * Exception thrown when an attempt is made to create a user that already exists.
 *
 * This exception should be used in scenarios where user registration or creation
 * fails due to a duplicate email.
 *
 */
@Getter
public class DuplicateAccountException extends RuntimeException {

    private final String contraintName;

    /**
     * Constructs a new {@code DuplicateUserException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public DuplicateAccountException(String message, String contraintName) {
        super(message);
        this.contraintName = contraintName;
    }
}