package com.authorization.server.domain.token;

import java.time.Instant;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Setter;

@Entity
@Table(name = "ACTIVATION_TOKEN")
@Setter(AccessLevel.PRIVATE)
public final class AccountActivationToken extends AccountToken {

    public AccountActivationToken(TokenType token, Instant createdAt, Instant expiresAt) {
        super(token, createdAt, expiresAt);
    }

    public AccountActivationToken() {
        this(TokenType.EMPTY, Instant.EPOCH, Instant.EPOCH);
    }

    @Override
    public TokenType getToken() {
        return tokenType;
    }
}