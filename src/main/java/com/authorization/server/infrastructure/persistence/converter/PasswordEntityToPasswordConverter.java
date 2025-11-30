package com.authorization.server.infrastructure.persistence.converter;

import com.authorization.server.domain.account.Password;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.PasswordEntity;

public class PasswordEntityToPasswordConverter implements Converter<PasswordEntity, Password> {
    @Override
    public Password convert(PasswordEntity fromSource) {
        return null;
    }

    @Override
    public PasswordEntity reverse(Password fromTarget) {
        return null;
    }
}
