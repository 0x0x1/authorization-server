package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import com.authorization.server.core.constant.EntityConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddressEntity {

    @NotNull
    @Column(name = EntityConstants.EMAIL_ADDRESS, nullable = false, unique = true)
    private String emailAddress;
}