package com.authorization.server.infrastructure.persistence.jpa;

public class UnsupportedConversion extends RuntimeException {
    public UnsupportedConversion(String message) {
        super(message);
    }
}
