package com.authorization.server.infrastructure.persistence.converter;

import java.util.Collection;
import java.util.UUID;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.application.command.RegisterCommandResult;
import com.authorization.server.application.command.RoleCommand;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.Credentials;
import com.authorization.server.identity.EmailAddress;
import com.authorization.server.identity.Password;
import com.authorization.server.identity.Username;
import com.authorization.server.infrastructure.persistence.entity.authorization.RoleEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.AccountEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.CredentialsEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.EmailAddressEntity;

@Component
public class AccountFactory {

    private final ConversionService conversionService;

    public AccountFactory(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public Account from(RegisterCommand source, Collection<UUID> roleIds) {
        if (source == null) {
            return null;
        }

        var username = new Username(source.username());
        var password = new Password(source.password());

        return Account.builder()
                .credentials(new Credentials(username, password))
                .emailAddress(new EmailAddress(source.email()))
                .roleIds(roleIds)
                .build();
    }

    public AccountEntity from(Account source, Collection<RoleEntity> roleEntities) {
        if (source == null) {
            return null;
        }

        var credentialsEntity = conversionService.convert(source.getCredentials(), CredentialsEntity.class);
        var emailAddressEntity = conversionService.convert(source.getEmailAddress(), EmailAddressEntity.class);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCredentials(credentialsEntity);
        accountEntity.setEmailAddress(emailAddressEntity);
        accountEntity.setRoleEntities(roleEntities);
        accountEntity.setLifecycleStatus(source.getAccountLifecycleStatus());
        accountEntity.setLockStatus(source.getAccountLockStatus());
        accountEntity.setCreatedAt(source.getCreatedAt());

        return accountEntity;
    }

    public RegisterCommandResult toRegisterCommandResult(Account source, Collection<RoleEntity> roleEntities) {
        if (source == null) {
            return null;
        }

        var username = source.getCredentials().username().username();
        var emailAddress = source.getEmailAddress().emailAddress();

        Collection<RoleCommand> roleCommands = roleEntities.stream()
                .map(roleEntity -> conversionService.convert(roleEntity, RoleCommand.class))
                .toList();

        return new RegisterCommandResult(source.getId().toString(),
                username, emailAddress, roleCommands);

    }
}