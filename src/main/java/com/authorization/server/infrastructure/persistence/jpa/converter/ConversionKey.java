package com.authorization.server.infrastructure.persistence.jpa.converter;

public record ConversionKey(Class<?> source, Class<?> target ) {

    static ConversionKey of(Class<?> source, Class<?> target) {
        return new ConversionKey(source, target);
    }
}
