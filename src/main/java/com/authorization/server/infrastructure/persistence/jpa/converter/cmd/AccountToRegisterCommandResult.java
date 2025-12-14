package com.authorization.server.infrastructure.persistence.jpa.converter.cmd;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.authorization.server.application.command.RegisterCommandResult;
import com.authorization.server.application.port.outbound.Converter;
import com.authorization.server.identity.Account;
import com.authorization.server.infrastructure.persistence.jpa.converter.dto.RoleConverter;
import com.authorization.server.infrastructure.persistence.jpa.converter.entity.RoleEntityConverter;
import com.authorization.server.infrastructure.persistence.jpa.repository.RoleRepository;
import com.authorization.server.web.dto.RoleDto;

@Component
public class AccountToRegisterCommandResult implements Converter<Account, RegisterCommandResult> {

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;
    private final RoleEntityConverter roleEntityConverter;

    public AccountToRegisterCommandResult(RoleRepository roleRepository, RoleConverter roleConverter, RoleEntityConverter roleEntityConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
        this.roleEntityConverter = roleEntityConverter;
    }

    @Override
    public RegisterCommandResult convert(Account source) {
        var username = source.getCredentials().username().username();
        var emailAddress = source.getEmailAddress().emailAddress();
        System.out.println(emailAddress);
        Collection<RoleDto> roles = source.getRoleIds().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(roleEntityConverter::toDomain)
                .map(roleConverter::convert)
                .toList();

        return new RegisterCommandResult(source.getId().toString(), username, emailAddress, roles);
    }
}
