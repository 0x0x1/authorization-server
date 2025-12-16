package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.domain.Credentials;
import com.authorization.server.infrastructure.persistence.entity.identity.CredentialsEntity;

@Component
public class CredentialsToCredentialsEntity implements Converter<Credentials, CredentialsEntity> {

    private final UsernameToUsernameEntity usernameConverter;
    private final PasswordToPasswordEntity passwordConverter;

    public CredentialsToCredentialsEntity(UsernameToUsernameEntity usernameConverter, PasswordToPasswordEntity passwordConverter) {
        this.usernameConverter = usernameConverter;
        this.passwordConverter = passwordConverter;
    }

    @Override
    public CredentialsEntity convert(Credentials source) {
        if (source == null) {
            return null;
        }

        var usernameEntity = usernameConverter.convert(source.username());
        var passwordEntity = passwordConverter.convert(source.password());
        return new CredentialsEntity(usernameEntity, passwordEntity);
    }
}