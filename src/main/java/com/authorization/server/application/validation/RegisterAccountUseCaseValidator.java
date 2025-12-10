package com.authorization.server.application.validation;

import org.springframework.stereotype.Component;

import com.authorization.server.identity.BusinessRules;
import com.authorization.server.web.dto.RegisterRequestDto;
import com.authorization.server.application.exception.ValidationException;

@Component
public class RegisterAccountUseCaseValidator implements Validator<RegisterRequestDto> {

    @Override
    public void validateAndThrow(RegisterRequestDto input, ValidationContext ctx) {
        if (input.username() == null || input.username().isBlank()) {
            ctx.addError("username", "Username cannot be blank");
        }

        if (input.email() == null || !BusinessRules.isValidEmail(input.email())) {
            ctx.addError("email", "Email is invalid");
        }
        if (input.password() == null || !BusinessRules.isStrongPassword(input.password())) {
            ctx.addError("password", "Password must be at least 8 characters, have " +
                    "at least one upper, lower, digit, special");
        }

        if (ctx.hasErrors()) {
            throw new ValidationException(ctx.getErrors());
        }
    }
}