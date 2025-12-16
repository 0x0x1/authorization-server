package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.domain.Username;
import com.authorization.server.infrastructure.persistence.entity.identity.UsernameEntity;

@Component
public class UsernameToUsernameEntity implements Converter<Username, UsernameEntity> {

    @Override
    public UsernameEntity convert(Username source) {
        if (source == null) {
            return null;
        }

        return new UsernameEntity(source.username());
    }
}