package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.domain.Credentials;
import com.authorization.server.domain.Password;
import com.authorization.server.domain.Username;
import com.authorization.server.infrastructure.persistence.entity.identity.CredentialsEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.PasswordEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.UsernameEntity;

@Component
public class CredentialsEntityToCredentials implements Converter<CredentialsEntity, Credentials> {

    private final Converter<UsernameEntity, Username> usernameConverter;
    private final Converter<PasswordEntity, Password> passwordConverter;

    public CredentialsEntityToCredentials(Converter<UsernameEntity, Username> usernameConverter, Converter<PasswordEntity, Password> passwordConverter) {
        this.usernameConverter = usernameConverter;
        this.passwordConverter = passwordConverter;
    }

    @Override
    public Credentials convert(CredentialsEntity source) {
        if (source == null) {
            return null;
        }

        var username = usernameConverter.convert(source.getUsername());
        var password = passwordConverter.convert(source.getPassword());
        return new Credentials(username, password);
    }
}