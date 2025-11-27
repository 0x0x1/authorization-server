package com.authorization.server.infrastructure.persistence.jpa.entity.account;

import java.util.Set;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AccountEntity {

    public AccountEntity() {}

    @Id
    @GeneratedValue
    protected UUID id;

    @Column(nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;

    @Column(unique = true, nullable = false)
    protected String email;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleTypeEntity> roleTypeEntities;

    @Column(nullable = false)
    protected Boolean isAccountEnabled;

    @Column(nullable = false)
    protected Boolean isAccountPasswordExpired;

    @Embedded
    protected AccountStateEntity accountStateEntity;
}