package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.domain.Username;
import com.authorization.server.infrastructure.persistence.entity.identity.UsernameEntity;

@Component
public class UsernameEntityToUsername implements Converter<UsernameEntity, Username> {

    @Override
    public Username convert(UsernameEntity source) {
        if (source == null) {
            return null;
        }

        return new Username(source.getUsername());
    }
}
