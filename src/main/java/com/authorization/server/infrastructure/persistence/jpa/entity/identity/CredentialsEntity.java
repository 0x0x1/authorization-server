package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;

@Embeddable
@AllArgsConstructor
public class CredentialsEntity {

    @NotNull
    @Embedded
    private UsernameEntity username;

    @NotNull
    @Embedded
    private PasswordEntity password;

    public CredentialsEntity() {}
}