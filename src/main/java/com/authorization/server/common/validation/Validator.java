package com.authorization.server.common.validation;

public interface Validator<T> {

    void validateAndThrow(T input, ValidationContext ctx);
}
