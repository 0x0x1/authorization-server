package com.authorization.server.infrastructure.persistence.jpa;

public class TypeResolutionException extends RuntimeException {
    public TypeResolutionException(String message) {
        super(message);
    }
}
