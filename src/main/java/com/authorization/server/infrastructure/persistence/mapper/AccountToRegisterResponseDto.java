package com.authorization.server.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.authorization.server.domain.account.Account;
import com.authorization.server.infrastructure.web.payload.RegisterResponseDto;

@Component
public class AccountToRegisterResponseDto implements Mapper<Account, RegisterResponseDto> {
    @Override
    public RegisterResponseDto convert(Account dataContainer) {
        return new RegisterResponseDto(dataContainer.getUsername(),
                dataContainer.getPassword(),
                dataContainer.getEmail(),
                dataContainer.getRoleTypes());
    }
}