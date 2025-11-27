package com.authorization.server.application.service;

import java.time.Instant;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.authorization.server.application.dto.RegisterCommand;
import com.authorization.server.domain.account.Account;
import com.authorization.server.domain.account.AccountRepository;
import com.authorization.server.domain.account.AccountState;
import com.authorization.server.domain.account.AccountStatus;

public class AccountRegistrationService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountRegistrationService(AccountRepository accountRepository,
                                      PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account register(RegisterCommand cmd) {
        var accountState = new AccountState(AccountStatus.INACTIVE, Instant.now());
        var userAcct = Account.builder()
                    .username(cmd.username())
                    .password(passwordEncoder.encode(cmd.password()))
                    .email(cmd.email())
                    .roleTypes(cmd.roleType())
                    .accountState(accountState)
                    .isAccountEnabled(false)
                    .isAccountPasswordExpired(false)
                    .hasPermission(false)
                    .build();


        System.out.println("USER_ACCOUNT: " + userAcct);

        Account persistedAcct =  accountRepository.save(userAcct).orElseThrow(() -> new RuntimeException("Account could not be saved"));
        accountRepository.flush(); // force sql command
        return persistedAcct;
    }
}