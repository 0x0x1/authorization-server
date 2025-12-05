package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class EmailAddressEntity {

    @NotNull
    @Column(name = "EMAIL_ADDRESS", nullable = false, unique = true)
    private String email;

    public EmailAddressEntity() {}
}