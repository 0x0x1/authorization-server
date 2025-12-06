package com.authorization.server.web.dto;

import java.util.Collection;

public record RegisterRequestDto(
        String username,
        String password,
        String email,
        Collection<String> roles
) {
}
