package com.authorization.server.infrastructure.web.payload;

import java.util.Set;

public record RegisterRequestDto(
        String username,
        String password,
        String email,
        Set<String> roleTypes
) {
}
