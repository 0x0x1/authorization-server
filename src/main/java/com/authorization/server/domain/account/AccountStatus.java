package com.authorization.server.domain.account;

public enum AccountStatus {
    ACTIVE,
    INACTIVE,
    EMPTY;

    Boolean isActive() {
        return this == ACTIVE;
    }

    Boolean isInactive() {
        return this == INACTIVE;
    }
}