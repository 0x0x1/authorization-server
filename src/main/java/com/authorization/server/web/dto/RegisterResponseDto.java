package com.authorization.server.web.dto;

import java.util.Collection;
import com.authorization.server.identity.Role;

public record RegisterResponseDto(
        String id,
        String username,
        String email,
        Collection<Role> role
) { }