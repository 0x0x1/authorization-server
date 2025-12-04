package com.authorization.server.core.validation;

public interface Validator<T> {

    void validate(T target, ValidationContext ctx);
}
