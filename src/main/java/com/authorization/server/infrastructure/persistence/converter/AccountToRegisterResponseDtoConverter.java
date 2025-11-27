package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.stereotype.Component;

import com.authorization.server.domain.account.Account;
import com.authorization.server.infrastructure.web.payload.RegisterResponseDto;

@Component
public class AccountToRegisterResponseDtoConverter implements Converter<Account, RegisterResponseDto> {
    @Override
    public RegisterResponseDto convert(Account fromSource) {
        return new RegisterResponseDto(fromSource.getUsername(),
                fromSource.getPassword(),
                fromSource.getEmail(),
                fromSource.getRoleTypes());
    }

    @Override
    public Account reverse(RegisterResponseDto fromTarget) {
        throw new UnsupportedOperationException();
    }
}