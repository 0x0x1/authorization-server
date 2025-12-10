package com.authorization.server.web.exception;

import java.util.List;

import com.authorization.server.web.api.response.Message;

import lombok.Getter;

@Getter
public class ApiValidationException extends RuntimeException {

    List<Message> errors;

    public ApiValidationException(List<Message> errors) {
        super("validation failed: " + errors);
        this.errors = errors;
    }
}
