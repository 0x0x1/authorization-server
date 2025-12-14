package com.authorization.server.infrastructure.persistence.jpa.converter.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.Converter;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.Credentials;
import com.authorization.server.identity.EmailAddress;
import com.authorization.server.identity.Password;
import com.authorization.server.identity.Username;
import com.authorization.server.infrastructure.persistence.jpa.repository.RoleRepository;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleEntity;
import com.authorization.server.web.dto.RegisterRequestDto;

@Component
public class RegisterRequestToAccountConverter implements Converter<RegisterRequestDto, Account> {

    private final RoleRepository roleRepository;

    public RegisterRequestToAccountConverter(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Account convert(RegisterRequestDto source) {
        Objects.requireNonNull(source);

        Username username = new Username(source.username());
        Password password = new Password(source.password());
        Credentials credentials = new Credentials(username, password);

        List<UUID> roleIds = source.roles().stream()
                .map(roleRepository::findByDisplayName)
                .map(RoleEntity::getId).toList();

        return Account.builder()
                .credentials(credentials)
                .emailAddress(new EmailAddress(source.email()))
                .roleIds(roleIds)
                .build();
    }
}