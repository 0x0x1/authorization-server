package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.domain.EmailAddress;
import com.authorization.server.infrastructure.persistence.entity.identity.EmailAddressEntity;

@Component
public class EmailAddressEntityToEmailAddress implements Converter<EmailAddressEntity, EmailAddress> {

    @Override
    public EmailAddress convert(EmailAddressEntity source) {
        if (source == null) {
            return null;
        }

        return new EmailAddress(source.getEmailAddress());
    }
}
