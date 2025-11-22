package com.authorization.server.domain.account;

public enum AccountStatus {
    ACTIVE,
    INACTIVE;

    Boolean isActive() {
        return this == ACTIVE;
    }

    Boolean isInactive() {
        return this == INACTIVE;
    }
}