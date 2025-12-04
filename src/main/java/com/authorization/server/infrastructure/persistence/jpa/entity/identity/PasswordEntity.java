package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class PasswordEntity {

    private String password;

    public PasswordEntity() {}
}
