package com.authorization.server.infrastructure.persistence.entity.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import com.authorization.server.infrastructure.persistence.constant.Jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class UsernameEntity {

    @NotNull
    @Column(name = Jpa.Column.USERNAME, nullable = false, unique = true)
    private String username;

    public UsernameEntity() {}
}