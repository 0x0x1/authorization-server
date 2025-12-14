package com.authorization.server.application.usecase;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

import java.util.Objects;
import java.util.Optional;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.application.command.RegisterCommandResult;
import com.authorization.server.application.port.inbound.RegisterAccountUseCase;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.converter.cmd.AccountToRegisterCommandResult;
import com.authorization.server.infrastructure.persistence.jpa.converter.cmd.RegisterCommandToAccount;

@Service
public class RegisterAccountUseCaseImpl implements RegisterAccountUseCase {

    private final AccountRepository accountRepository;
    private final RegisterCommandToAccount cmdConverter;
    private final AccountToRegisterCommandResult toRegisterCommandResult;

    public RegisterAccountUseCaseImpl(AccountRepository accountRepository, RegisterCommandToAccount cmdConverter, AccountToRegisterCommandResult toRegisterCommandResult) {
        this.accountRepository = accountRepository;
        this.cmdConverter = cmdConverter;
        this.toRegisterCommandResult = toRegisterCommandResult;
    }


    @Override
    @Transactional(REQUIRES_NEW)
    public RegisterCommandResult handle(RegisterCommand registerCommand) {
        Objects.requireNonNull(registerCommand);
        Account Account = cmdConverter.convert(registerCommand);
        System.out.println(Account.getEmailAddress().emailAddress());
        Optional<Account> saved = accountRepository.save(Account);

        if (saved.isEmpty()) {
            throw new IllegalStateException("Account could not be saved");
        }

        return toRegisterCommandResult.convert(Account);
    }
}