package com.authorization.server.infrastructure.persistence.jpa.entity.account;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import com.authorization.server.domain.account.RoleType;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class AccountEntity {

    protected AccountEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Column(unique = true, nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;

    @Column(unique = true, nullable = false)
    protected String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Embedded
    protected AccountStateEntity accountStateEntity;
}
