package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
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