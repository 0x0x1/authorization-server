package com.authorization.server.application.dto;

import java.util.Set;

import com.authorization.server.domain.account.RoleType;

public record RegisterCommand(
        String username,
        String password,
        String email,
        Set<RoleType> roleType) {
}
