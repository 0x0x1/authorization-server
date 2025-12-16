package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.domain.Password;
import com.authorization.server.infrastructure.persistence.entity.identity.PasswordEntity;

@Component
public class PasswordToPasswordEntity implements Converter<Password, PasswordEntity> {

    @Override
    public PasswordEntity convert(Password source) {
        if (source == null) {
            return null;
        }

        return new PasswordEntity(source.password());
    }
}