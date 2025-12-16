package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.RegisterCommandResult;
import com.authorization.server.application.command.RoleCommand;
import com.authorization.server.web.dto.RegisterResponseDto;
import com.authorization.server.web.dto.RoleDto;

@Component
public class RegisterCommandResultToRegisterResponseDto implements Converter<RegisterCommandResult, RegisterResponseDto> {

    private final Converter<RoleCommand, RoleDto> roleConverter;

    public RegisterCommandResultToRegisterResponseDto(Converter<RoleCommand, RoleDto> roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public RegisterResponseDto convert(RegisterCommandResult source) {
        if (source == null) {
            return null;
        }

        return new RegisterResponseDto(source.id(),
                source.username(),
                source.email(),
                source.role().stream().map(roleConverter::convert).toList());
    }
}