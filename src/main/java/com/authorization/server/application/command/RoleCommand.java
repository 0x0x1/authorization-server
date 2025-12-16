package com.authorization.server.application.command;

import java.util.Collection;

public record RoleCommand(String displayName, String description, Collection<PermissionCommand> permissions) {
}
