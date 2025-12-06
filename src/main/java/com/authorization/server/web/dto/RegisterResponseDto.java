package com.authorization.server.web.dto;

import java.util.Collection;
import java.util.Set;

import com.authorization.server.identity.EmailAddress;
import com.authorization.server.identity.Password;
import com.authorization.server.identity.Role;
import com.authorization.server.identity.Username;

public record RegisterResponseDto(
        String username,
        String email,
        Collection<Role> role
) { }