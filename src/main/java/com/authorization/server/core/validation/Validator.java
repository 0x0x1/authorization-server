package com.authorization.server.core.validation;

import com.authorization.server.application.command.Command;

public interface Validator<T extends Command> {

    void validate(T target, ValidationContext ctx);
}
