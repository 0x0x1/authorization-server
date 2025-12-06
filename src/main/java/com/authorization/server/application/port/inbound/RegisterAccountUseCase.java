package com.authorization.server.application.port.inbound;

import java.util.Optional;

import com.authorization.server.identity.Account;
import com.authorization.server.web.dto.RegisterRequestDto;
import com.authorization.server.web.dto.RegisterResponseDto;

/**
 * Application boundary. Entry point to Application capability. Inbound port.
 */
public interface RegisterAccountUseCase {

    RegisterResponseDto register(RegisterRequestDto account);
}
