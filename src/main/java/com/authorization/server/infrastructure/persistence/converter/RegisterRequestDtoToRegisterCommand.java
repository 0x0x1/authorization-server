package com.authorization.server.infrastructure.persistence.converter;

import java.util.Objects;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.web.dto.RegisterRequestDto;

@Component
public class RegisterRequestDtoToRegisterCommand implements Converter<RegisterRequestDto, RegisterCommand> {

    @Override
    public RegisterCommand convert(@NonNull RegisterRequestDto source) {
        Objects.requireNonNull(source, "RegisterRequestDto must not be null");
        Objects.requireNonNull(source.roles(), "Roles must not be null");

        return new RegisterCommand(source.username(),
                source.password(), source.email(), source.roles());
    }
}