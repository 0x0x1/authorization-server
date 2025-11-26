package com.authorization.server.application.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.authorization.server.application.dto.RegisterCommand;
import com.authorization.server.domain.account.Account;
import com.authorization.server.domain.account.AccountRepository;

public class AccountRegistrationService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountRegistrationService(AccountRepository accountRepository,
                                      PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account register(RegisterCommand cmd) {
        var userAcct = Account.builder()
                    .username(cmd.username())
                    .password(passwordEncoder.encode(cmd.password()))
                    .email(cmd.email())
                    .roleTypes(cmd.roleType())
                    .build();

        Account persistedAcct =  accountRepository.save(userAcct).orElseThrow();
        accountRepository.flush(); // force sql command
        return persistedAcct;
    }
}