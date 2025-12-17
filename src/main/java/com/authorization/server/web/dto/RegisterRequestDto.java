package com.authorization.server.web.dto;

import java.util.Collection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.authorization.server.web.validation.annotation.ValidateEmail;

import lombok.Builder;

@Builder
public record RegisterRequestDto(
        @NotNull @NotBlank
        String username,
        @NotNull @NotBlank @Size(min = 8, max = 100)
        String password,
        @NotNull @NotBlank @ValidateEmail @Size(max = 100)
        String email,
        @NotNull @Size(min = 1)
        Collection<@NotBlank String> roles
) {
}