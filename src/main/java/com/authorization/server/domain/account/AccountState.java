package com.authorization.server.domain.account;

import java.time.Instant;

import lombok.Getter;

@Getter
public class AccountState {

    private AccountStatus accountStatus;
    private Instant activatedAt;

    public AccountState(AccountStatus accountStatus, Instant activatedAt) {
        this.accountStatus = accountStatus;
        this.activatedAt = activatedAt;
    }

    void enable() {
        if (accountStatus.isActive()) {
            throw new IllegalStateException("Account is already active");
        }
        this.accountStatus = AccountStatus.ACTIVE;
        this.activatedAt = Instant.now();
    }

    void disable() {
        if (this.accountStatus.isInactive()) {
            throw new IllegalStateException("Account is already inactive");
        }
        this.accountStatus = AccountStatus.INACTIVE;
        this.activatedAt = Instant.now();
    }
}