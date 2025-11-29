package com.authorization.server.infrastructure.web.payload;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.authorization.server.domain.account.RoleType;

public record RegisterRequestDto(
        @NotBlank(message = "Username cannot be blank")
        @NotNull(message = "Username cannot be null")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @NotNull(message = "Password cannot be null")
        String password,

        @NotBlank(message = "Email cannot be blank")
        @NotNull(message = "Email cannot be null")
        String email,

        @NotNull(message = "Role cannot be null")
        @NotEmpty(message = "Role cannot be empty")
        Set<RoleType> roleTypes) {
}