package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.identity.Account;
import com.authorization.server.identity.Credentials;
import com.authorization.server.identity.EmailAddress;
import com.authorization.server.infrastructure.persistence.entity.BaseEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.AccountEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.CredentialsEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.EmailAddressEntity;

@Component
public class AccountEntityToAccount implements Converter<AccountEntity, Account> {

    private final Converter<CredentialsEntity, Credentials> credentialsConverter;
    private final Converter<EmailAddressEntity, EmailAddress> emailAddressConverter;

    public AccountEntityToAccount(Converter<CredentialsEntity, Credentials> credentialsConverter, Converter<EmailAddressEntity, EmailAddress> emailAddressConverter) {
        this.credentialsConverter = credentialsConverter;
        this.emailAddressConverter = emailAddressConverter;
    }

    @Override
    public Account convert(AccountEntity source) {
        if (source == null) {
            return null;
        }
        return Account.builder()
                .roleIds(source.getRoleEntities().stream().map(BaseEntity::getId).toList())
                .credentials(credentialsConverter.convert(source.getCredentials()))
                .emailAddress(emailAddressConverter.convert(source.getEmailAddress()))
                .id(source.getId())
                .accountLifecycleStatus(source.getLifecycleStatus())
                .accountLockStatus(source.getLockStatus())
                .createdAt(source.getCreatedAt())
                .build();
    }
}