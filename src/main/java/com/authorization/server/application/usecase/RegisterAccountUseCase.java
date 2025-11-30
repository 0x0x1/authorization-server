package com.authorization.server.application.usecase;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

import java.time.Instant;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.application.port.outbound.PasswordHashingService;
import com.authorization.server.core.validation.RegisterAccountUseCaseValidator;
import com.authorization.server.core.validation.ValidationContext;
import com.authorization.server.domain.account.Account;
import com.authorization.server.domain.account.AccountRepository;
import com.authorization.server.domain.account.AccountState;
import com.authorization.server.domain.account.AccountStatus;
import com.authorization.server.domain.account.Password;

@Service
public class RegisterAccountUseCase implements com.authorization.server.application.port.inbound.RegisterAccountUseCase {

    private static final String REGISTER_COMMAND = "registerCommand";

    private final AccountRepository accountRepository;
    private final RegisterAccountUseCaseValidator validator;
    private final PasswordHashingService passwordEncoder;

    public RegisterAccountUseCase(AccountRepository accountRepository, RegisterAccountUseCaseValidator validator,
                                  PasswordHashingService passwordEncoder) {
        this.accountRepository = accountRepository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public Account register(RegisterCommand cmd) {
        var ctx = new ValidationContext(REGISTER_COMMAND);
        validator.validate(cmd, ctx);

        var accountState = new AccountState(AccountStatus.INACTIVE, Instant.now());
        var hashedPassword = new Password(passwordEncoder.hash(cmd.password().getPassword()));
        var userAcct = Account.builder()
                    .username(cmd.username())
                    .password(hashedPassword)
                    .email(cmd.email())
                    .roleTypes(cmd.roleType())
                    .accountState(accountState)
                    .isAccountEnabled(false)
                    .isAccountPasswordExpired(false)
                    .build();

        return accountRepository.save(userAcct).orElseThrow(() ->
                new RuntimeException("Account could not be saved"));
    }
}