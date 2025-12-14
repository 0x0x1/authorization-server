package com.authorization.server.authorization;

import java.time.Instant;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract sealed class AccountToken implements Token permits AccountActivationToken, AccountResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    protected final TokenType tokenType;
    protected final Instant createdAt;
    protected final Instant expiresAt;

    protected AccountToken(TokenType tokenType, Instant createdAt, Instant expiresAt) {
        this.tokenType = tokenType;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}