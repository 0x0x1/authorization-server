package com.authorization.server.infrastructure.web.api.delegator;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.authorization.server.application.dto.RegisterCommand;
import com.authorization.server.application.service.AccountRegistrationService;
import com.authorization.server.domain.account.Account;
import com.authorization.server.infrastructure.persistence.mapper.Mapper;
import com.authorization.server.infrastructure.web.payload.RegisterRequestDto;
import com.authorization.server.infrastructure.web.payload.RegisterResponseDto;

@Component
public class ApiProcessor {

    private final AccountRegistrationService service;
    private final Mapper<RegisterRequestDto, RegisterCommand> cmdMapper;
    private final Mapper<Account, RegisterResponseDto> acctMapper;

    public ApiProcessor(AccountRegistrationService service, Mapper<RegisterRequestDto, RegisterCommand> cmdMapper, Mapper<Account, RegisterResponseDto> acctMapper) {
        this.service = service;
        this.cmdMapper = cmdMapper;
        this.acctMapper = acctMapper;
    }

    public RegisterResponseDto registerAccount(RegisterRequestDto requestDto) {
        Assert.notNull(requestDto, "RegisterRequestDto must not be null");
        var registerCmd = cmdMapper.convert(requestDto);
        var acct = service.register(registerCmd);
        return acctMapper.convert(acct);
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