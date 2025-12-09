package com.authorization.server.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import com.authorization.server.core.constant.EntityConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @NotNull
    @Column(name = EntityConstants.DISPLAY_NAME, nullable = false)
    private String displayName;

    @NotNull
    @Column(name = EntityConstants.DESCRIPTION, nullable = false)
    private String description;
}
