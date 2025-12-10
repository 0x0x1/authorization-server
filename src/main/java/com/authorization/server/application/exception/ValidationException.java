package com.authorization.server.application.exception;

import java.util.List;

import com.authorization.server.web.api.response.Message;

public class ValidationException extends RuntimeException {

    private final List<Message> errors;

    public ValidationException(List<Message> errors) {
        super("validation failed: " + errors);
        this.errors = errors;
    }

    public List<Message> getErrors() {
        return errors;
    }
}