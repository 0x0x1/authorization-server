package com.authorization.server.core.validation;

import org.springframework.stereotype.Component;

import com.authorization.server.infrastructure.web.dto.RegisterRequestDto;

@Component
public class RegisterAccountUseCaseValidator extends MessageValidation implements Validator<RegisterRequestDto> {

    @Override
    public void validate(RegisterRequestDto target, ValidationContext ctx) {
        throw new UnsupportedOperationException();
    }
}