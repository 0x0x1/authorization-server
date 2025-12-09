package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import com.authorization.server.core.constant.EntityConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class UsernameEntity {

    @NotNull
    @Column(name = EntityConstants.USERNAME, nullable = false, unique = true)
    private String username;

    public UsernameEntity() {}
}