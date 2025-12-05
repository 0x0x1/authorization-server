package com.authorization.server.infrastructure.web.dto;

import java.util.Set;

public record RegisterRequestDto(
        String username,
        String password,
        String email,
        Set<String> roleTypes
) {
}
