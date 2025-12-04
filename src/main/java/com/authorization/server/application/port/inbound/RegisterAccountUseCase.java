package com.authorization.server.application.port.inbound;

import java.util.Optional;

import com.authorization.server.identity.Account;
import com.authorization.server.infrastructure.web.payload.RegisterRequestDto;

/**
 * Application boundary. Entry point to Application capability. Inbound port.
 */
public interface RegisterAccountUseCase {

    Optional<Account> register(RegisterRequestDto cmd);
}
