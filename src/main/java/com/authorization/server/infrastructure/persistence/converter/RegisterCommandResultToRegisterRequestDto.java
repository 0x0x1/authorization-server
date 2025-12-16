package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.RegisterCommandResult;
import com.authorization.server.application.command.RoleCommand;
import com.authorization.server.web.dto.RegisterRequestDto;

@Component
public class RegisterCommandResultToRegisterRequestDto implements Converter<RegisterCommandResult, RegisterRequestDto> {
    @Override
    public RegisterRequestDto convert(RegisterCommandResult source) {
        if (source == null) {
            return null;
        }

        return new RegisterRequestDto(source.id(), source.username(),
                source.email(), source.role().stream().map(RoleCommand::displayName).toList());
    }
}
