package com.authorization.server.infrastructure.persistence.jpa.converter.dto;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.Converter;
import com.authorization.server.identity.Account;
import com.authorization.server.infrastructure.persistence.jpa.converter.entity.RoleEntityConverter;
import com.authorization.server.infrastructure.persistence.jpa.repository.RoleRepository;
import com.authorization.server.web.dto.RegisterResponseDto;
import com.authorization.server.web.dto.RoleDto;

@Component
public class AccountToRegisterResponseConverter implements Converter<Account, RegisterResponseDto> {

    private final RoleRepository roleRepository;
    private final RoleEntityConverter roleEntityConverter;
    private final RoleConverter roleDtoConverter;

    public AccountToRegisterResponseConverter(RoleRepository roleRepository, RoleEntityConverter roleEntityConverter, RoleConverter roleDtoConverter) {
        this.roleRepository = roleRepository;
        this.roleEntityConverter = roleEntityConverter;
        this.roleDtoConverter = roleDtoConverter;
    }

    @Override
    public RegisterResponseDto convert(Account source) {
        Objects.requireNonNull(source);

        String accountId = source.getId().toString();
        String username = source.getCredentials().username().username();
        String emailAddress = source.getEmailAddress().emailAddress();
        Collection<RoleDto> roleDtos = source.getRoleIds().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(roleEntityConverter::toDomain)
                .map(roleDtoConverter::convert)
                .toList();

        return new RegisterResponseDto(accountId, username, emailAddress, roleDtos);
    }
}