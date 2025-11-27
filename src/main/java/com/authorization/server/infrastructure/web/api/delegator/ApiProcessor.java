package com.authorization.server.infrastructure.web.api.delegator;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.authorization.server.application.dto.RegisterCommand;
import com.authorization.server.application.service.AccountRegistrationService;
import com.authorization.server.domain.account.Account;
import com.authorization.server.infrastructure.persistence.converter.Converter;
import com.authorization.server.infrastructure.web.payload.RegisterRequestDto;
import com.authorization.server.infrastructure.web.payload.RegisterResponseDto;

@Component
public class ApiProcessor {

    private final AccountRegistrationService service;
    private final Converter<RegisterRequestDto, RegisterCommand> cmdConverter;
    private final Converter<Account, RegisterResponseDto> converter;

    public ApiProcessor(AccountRegistrationService service, Converter<RegisterRequestDto, RegisterCommand> cmdConverter, Converter<Account, RegisterResponseDto> converter) {
        this.service = service;
        this.cmdConverter = cmdConverter;
        this.converter = converter;
    }

    public RegisterResponseDto registerAccount(RegisterRequestDto requestDto) {
        Assert.notNull(requestDto, "RegisterRequestDto must not be null");
        var registerCmd = cmdConverter.convert(requestDto);
        var acct = service.register(registerCmd);
        return converter.convert(acct);
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