package com.authorization.server.infrastructure.persistence.converter;

public interface Converter<DOMAIN, ENTITY> {
    ENTITY toEntity(DOMAIN fromSource);
    DOMAIN toDomain(ENTITY fromTarget);
}