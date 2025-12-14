package com.authorization.server.infrastructure.persistence.jpa.converter.cmd;

import org.springframework.stereotype.Component;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.application.port.outbound.Converter;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.Credentials;
import com.authorization.server.identity.EmailAddress;
import com.authorization.server.identity.Password;
import com.authorization.server.identity.Username;
import com.authorization.server.infrastructure.persistence.jpa.entity.BaseEntity;
import com.authorization.server.infrastructure.persistence.jpa.repository.RoleRepository;

@Component
public class RegisterCommandToAccount implements Converter<RegisterCommand, Account> {

    private final RoleRepository roleRepository;

    public RegisterCommandToAccount(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Account convert(RegisterCommand source) {
        var username = new Username(source.username());
        var password = new Password(source.password());
        var emailAddress = new EmailAddress(source.email());
        var credentials = new Credentials(username, password);
        var roleIds = source.roles().stream()
                .map(roleRepository::findByDisplayName)
                .map(BaseEntity::getId)
                .toList();

        return Account.builder()
                .credentials(credentials)
                .emailAddress(emailAddress)
                .roleIds(roleIds)
                .build();
    }
}