package com.authorization.server.authorization.token;

import java.time.Instant;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Setter;

@Entity
@Table(name = "RESET_TOKEN")
@Setter(AccessLevel.PRIVATE)
public final class AccountResetToken extends AccountToken {

    AccountResetToken(TokenType token, Instant createdAt, Instant expiresAt) {
        super(token, createdAt, expiresAt);
    }

    public AccountResetToken() {
        this(TokenType.EMPTY, Instant.EPOCH,Instant.EPOCH);
    }

    @Override
    public TokenType getToken() {
        throw new UnsupportedOperationException("coming soon");
    }
}