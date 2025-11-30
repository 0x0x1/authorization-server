package com.authorization.server.core.validation;

import static com.authorization.server.core.validation.ValidationRules.isStrongPassword;
import static com.authorization.server.core.validation.ValidationRules.isValidEmail;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.core.MessageSeverity;

@Component
public class RegisterAccountUseCaseValidator extends MessageValidation implements Validator<RegisterCommand> {

    @Override
    public void validate(RegisterCommand target, ValidationContext ctx) {
        if (target == null) {
            throw new IllegalArgumentException(COMMAND_CANNOT_BE_NULL);
        }

        if (target.roleType() == null) {
            ctx.addError(MessageSeverity.ERROR, ROLE_TYPE_REQUIRED);
        }

        if (target.username() == null || StringUtils.isBlank(target.username().getUsername())) {
            ctx.addError(MessageSeverity.ERROR, USERNAME_REQUIRED);
        }

        if (target.password() == null || target.password().getPassword() == null || StringUtils.isBlank(target.password().getPassword())) {
            ctx.addError(MessageSeverity.ERROR, PASSWORD_REQUIRED);
        }

        if (target.password() != null && target.password().getPassword() != null) {
            if (!isStrongPassword(target.password().getPassword())) {
                ctx.addError(MessageSeverity.ERROR, PASSWORD_INVALID);
            }
        }

        if (target.email() == null || StringUtils.isBlank(target.email().getEmailAddress())) {
            ctx.addError(MessageSeverity.ERROR, EMAIL_REQUIRED);
        }

        if (target.email() != null && target.email().getEmailAddress() != null) {
            if (!isValidEmail(target.email().getEmailAddress())) {
                ctx.addError(MessageSeverity.ERROR, EMAIL_INVALID);
            }
        }
    }
}