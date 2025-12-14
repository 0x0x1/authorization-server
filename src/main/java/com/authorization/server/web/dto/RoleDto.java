package com.authorization.server.web.dto;

import java.util.Collection;

public record RoleDto(String displayName, String description, Collection<PermissionDto> permissions) {
}
