package com.authorization.server.web.exception;

/**
 * Custom runtime exception thrown when a client exceeds the allowed number of API requests
 * as defined by the application's rate limiting policy.
 *
 * Typically used to signal HTTP 429 Too Many Requests.
 */
public class ApiRequestLimitException extends RuntimeException {

    /**
     * Constructs a new ApiRequestLimitException with the specified detail message.
     *
     * @param message the detail message explaining why the exception occurred.
     */
    public ApiRequestLimitException(String message) {
        super(message);
    }
}