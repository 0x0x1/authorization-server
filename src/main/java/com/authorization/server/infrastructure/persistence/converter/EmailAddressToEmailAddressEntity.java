package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.identity.EmailAddress;
import com.authorization.server.infrastructure.persistence.entity.identity.EmailAddressEntity;

@Component
public class EmailAddressToEmailAddressEntity implements Converter<EmailAddress, EmailAddressEntity> {

    @Override
    public EmailAddressEntity convert(EmailAddress source) {
        if (source == null) {
            return null;
        }

        return new EmailAddressEntity(source.emailAddress());
    }
}