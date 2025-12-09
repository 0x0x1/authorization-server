package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import com.authorization.server.core.constant.EntityConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class PasswordEntity {

    @Column(name = EntityConstants.PASSWORD, nullable = false, unique = true)
    private String password;

    public PasswordEntity() {}
}