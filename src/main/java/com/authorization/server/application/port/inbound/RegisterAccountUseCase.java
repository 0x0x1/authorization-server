package com.authorization.server.application.port.inbound;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.domain.account.Account;

/**
 * Application boundary. Entry point to Application capability. Inbound port.
 */
public interface RegisterAccountUseCase {

    Account register(RegisterCommand cmd);
}
