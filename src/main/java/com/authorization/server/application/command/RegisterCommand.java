package com.authorization.server.application.command;

import java.util.Collection;

public record RegisterCommand(
        String username,
        String password,
        String email,
        Collection<String> roles
) {
}
