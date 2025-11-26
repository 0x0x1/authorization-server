package com.authorization.server.infrastructure.persistence.jpa.entity.account;

import java.time.Instant;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.authorization.server.domain.account.AccountStatus;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AccountStateEntity {

    public AccountStateEntity() {}

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Instant activatedAt;
}