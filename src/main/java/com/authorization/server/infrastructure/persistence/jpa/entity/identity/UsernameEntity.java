package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class UsernameEntity {

    private String username;

    public UsernameEntity() {}
}
