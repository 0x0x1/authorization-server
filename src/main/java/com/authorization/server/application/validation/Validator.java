package com.authorization.server.application.validation;

public interface Validator<T> {

    void validateAndThrow(T input, ValidationContext ctx);
}
