package com.authorization.server.application.command;

import java.util.Collection;

import com.authorization.server.web.dto.RoleDto;

public record RegisterCommandResult(
        String id,
        String username,
        String email,
        Collection<RoleDto> role
) {
}
