package com.authorization.server.application.port.inbound;

import com.authorization.server.web.dto.RegisterRequestDto;
import com.authorization.server.web.dto.RegisterResponseDto;

/**
 * Application boundary. Entry point to Application capability. Inbound port.
 */
public interface RegisterAccountUseCase {

    //TODO: accept command and return return registerResult response
    RegisterResponseDto register(RegisterRequestDto account);
}
