package com.authorization.server.infrastructure.persistence;

public class UnsupportedConversion extends RuntimeException {
    public UnsupportedConversion(String message) {
        super(message);
    }
}
