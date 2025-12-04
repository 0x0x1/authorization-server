package com.authorization.server.infrastructure.web.payload;

import java.util.Set;

import com.authorization.server.identity.EmailAddress;
import com.authorization.server.identity.Password;
import com.authorization.server.identity.Role;
import com.authorization.server.identity.Username;

public record RegisterResponseDto(
        Username username,
        Password password,
        EmailAddress email,
        Set<Role> role
) { }