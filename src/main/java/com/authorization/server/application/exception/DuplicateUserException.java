package com.authorization.server.application.exception;

/**
 * Exception thrown when an attempt is made to create a user that already exists.
 *
 * This exception should be used in scenarios where user registration or creation
 * fails due to a duplicate email.
 *
 */
public class DuplicateUserException extends RuntimeException {

    /**
     * Constructs a new {@code DuplicateUserException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public DuplicateUserException(String message) {
        super(message);
    }
}