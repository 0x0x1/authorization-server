package com.authorization.server.infrastructure.persistence.entity.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import com.authorization.server.infrastructure.persistence.constant.Jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddressEntity {

    @NotNull
    @Column(name = Jpa.Column.EMAIL_ADDRESS, nullable = false, unique = true)
    private String emailAddress;
}