package com.authorization.server.infrastructure.web.payload;

import java.util.Set;

import com.authorization.server.domain.account.RoleType;

public record RegisterRequestDto(
        String username,
        String password,
        String email,
        Set<RoleType> role) {
}