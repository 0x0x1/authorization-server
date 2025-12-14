package com.authorization.server.application.validation;

import org.springframework.stereotype.Component;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.identity.BusinessRules;
import com.authorization.server.application.exception.ValidationException;

@Component
public class RegisterAccountUseCaseValidator implements Validator<RegisterCommand> {

    @Override
    public void validateAndThrow(RegisterCommand input, ValidationContext ctx) {
        if (input.username() == null || input.username().isBlank()) {
            ctx.addError("app.error.severity.info", "Username cannot be blank");
        }

        if (input.email() == null || !BusinessRules.isValidEmail(input.email())) {
            ctx.addError("app.error.severity.info", "Email is invalid");
        }
        if (input.password() == null || !BusinessRules.isStrongPassword(input.password())) {
            ctx.addError("app.error.severity.info", "Password must be at least 8 characters, have " +
                    "at least one upper, lower, digit, special");
        }

        if (ctx.hasErrors()) {
            throw new ValidationException(ctx.getErrors());
        }
    }
}