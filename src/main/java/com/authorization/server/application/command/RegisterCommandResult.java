package com.authorization.server.application.command;

import java.util.Collection;

public record RegisterCommandResult(
        String id,
        String username,
        String email,
        Collection<RoleCommand> role
) {
}
