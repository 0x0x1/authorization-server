package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class PasswordEntity {

    @Column(name = "PASSWORD", nullable = false, unique = true)
    private String password;

    public PasswordEntity() {}
}