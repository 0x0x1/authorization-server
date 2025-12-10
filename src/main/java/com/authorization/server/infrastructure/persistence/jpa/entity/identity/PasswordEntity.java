package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import com.authorization.server.infrastructure.persistence.jpa.constant.Jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class PasswordEntity {

    @Column(name = Jpa.Column.PASSWORD, nullable = false, unique = true)
    private String password;

    public PasswordEntity() {}
}