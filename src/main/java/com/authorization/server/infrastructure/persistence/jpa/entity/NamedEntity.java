package com.authorization.server.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import com.authorization.server.infrastructure.persistence.jpa.constant.Jpa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @NotNull
    @Column(name = Jpa.Column.DISPLAY_NAME, nullable = false)
    private String displayName;

    @NotNull
    @Column(name = Jpa.Column.DESCRIPTION, nullable = false)
    private String description;
}
