package com.authorization.server.infrastructure.persistence.jpa.converter.dto;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.DtoConverter;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.Role;
import com.authorization.server.infrastructure.persistence.jpa.converter.entity.RoleEntityConverter;
import com.authorization.server.infrastructure.persistence.jpa.repository.RoleRepository;
import com.authorization.server.web.dto.RegisterResponseDto;

@Component
public class AccountToRegisterResponseDtoConverter implements DtoConverter<Account, RegisterResponseDto> {

    private final RoleRepository roleRepository;
    private final RoleEntityConverter roleConverter;

    public AccountToRegisterResponseDtoConverter(RoleRepository roleRepository, RoleEntityConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    public RegisterResponseDto convert(Account account) {
        Objects.requireNonNull(account);

        String accountId = account.getId().toString();
        String username = account.getCredentials().username().username();
        String emailAddress = account.getEmailAddress().emailAddress();
        Collection<Role> roles = account.getRoleIds().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(roleConverter::toDomain)
                .toList();

        return new RegisterResponseDto(accountId, username, emailAddress, roles);
    }
}