package com.authorization.server.web.dto;

import java.util.Collection;

public record RegisterResponseDto(
        String id,
        String username,
        String email,
        Collection<RoleDto> role
) { }