package com.authorization.server.application.usecase;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

import java.util.Collection;
import java.util.UUID;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.application.command.RegisterCommandResult;
import com.authorization.server.application.port.inbound.RegisterAccountUseCase;
import com.authorization.server.application.port.outbound.RoleReaderPort;
import com.authorization.server.domain.Account;
import com.authorization.server.domain.AccountFactoryPort;
import com.authorization.server.domain.AccountPersistencePort;
import com.authorization.server.domain.Role;

@Service
public class RegisterAccountUseCaseImpl implements RegisterAccountUseCase {

    private final AccountPersistencePort accountPersistencePort;
    private final RoleReaderPort roleReaderPort;
    private final AccountFactoryPort accountFactoryConverterPort;

    public RegisterAccountUseCaseImpl(AccountPersistencePort accountPersistencePort, RoleReaderPort roleReaderPort, AccountFactoryPort accountFactoryConverterPort) {
        this.accountPersistencePort = accountPersistencePort;
        this.roleReaderPort = roleReaderPort;
        this.accountFactoryConverterPort = accountFactoryConverterPort;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public RegisterCommandResult handle(RegisterCommand registerCommand) {
        Collection<UUID> roleIds = roleReaderPort.retrieveRoleIdsByDisplayName(registerCommand.roles());

        Account account = accountFactoryConverterPort.from(registerCommand, roleIds); // create
        Account savedAccount = accountPersistencePort.save(account); // persist

        Collection<Role> roles = roleReaderPort.retrieveRolesByIds(savedAccount, roleIds);

        return accountFactoryConverterPort.toRegisterCommandResult(savedAccount, roles);
    }
}