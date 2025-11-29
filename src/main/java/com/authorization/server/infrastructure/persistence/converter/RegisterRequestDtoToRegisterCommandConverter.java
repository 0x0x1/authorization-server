package com.authorization.server.infrastructure.persistence.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.authorization.server.application.dto.RegisterCommand;
import com.authorization.server.infrastructure.web.payload.RegisterRequestDto;

@Component
public class RegisterRequestDtoToRegisterCommandConverter implements Converter<RegisterRequestDto, RegisterCommand> {

    @Override
    public RegisterCommand convert(RegisterRequestDto fromSource) {
        Objects.requireNonNull(fromSource);
        return new RegisterCommand(fromSource.username(),
                fromSource.password(),
                fromSource.email(),
                fromSource.roleTypes());
    }

    @Override
    public RegisterRequestDto reverse(RegisterCommand fromTarget) {
        return null;
    }
}