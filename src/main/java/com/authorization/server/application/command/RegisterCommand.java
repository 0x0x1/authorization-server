package com.authorization.server.application.command;

import java.util.Set;

import com.authorization.server.domain.account.EmailAddress;
import com.authorization.server.domain.account.Password;
import com.authorization.server.domain.account.RoleType;
import com.authorization.server.domain.account.Username;

public record RegisterCommand (
        Username username,
        Password password,
        EmailAddress email,
        Set<RoleType> roleType) implements Command {
}
