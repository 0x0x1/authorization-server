package com.authorization.server.infrastructure.web.api.delegator;

import java.net.URI;

import org.springframework.stereotype.Component;

import com.authorization.server.application.usecase.RegisterAccountUseCase;
import com.authorization.server.infrastructure.web.dto.RegisterRequestDto;
import com.authorization.server.infrastructure.web.dto.RegisterResponseDto;

@Component
public class ApiProcessor {

    private final RegisterAccountUseCase service;

    public ApiProcessor(RegisterAccountUseCase service) {
        this.service = service;
    }

    public RegisterResponseDto registerAccount(RegisterRequestDto requestDto) {
        return null;
    }

    /**
     * Builds a URI pointing to the newly created user resource.
     *
     * @param responseDto the response DTO containing the user's ID.
     * @return a {@link URI} of the created resource.
     */
    public URI getResourceLocation(RegisterResponseDto responseDto) {
        return null;
//        Assert.notNull(responseDto, "RegisterResponseDto must not be null");
//        var userId = responseDto.id();
//        return ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("/api/users/{id}") // subject to change.
//                .buildAndExpand(userId)
//                .toUri();
    }
}