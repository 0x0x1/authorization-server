package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import java.time.Instant;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.authorization.server.identity.AccountLifecycleStatus;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AccountStateEntity {

    public AccountStateEntity() {}

    @Enumerated(EnumType.STRING)
    private AccountLifecycleStatus accountLifecycleStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Instant activatedAt;
}