package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.authorization.server.identity.AccountLifecycleStatus;
import com.authorization.server.identity.AccountLockStatus;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "ACCOUNT", uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL_ADDRESS" ))
public class AccountEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "LIFECYCLE_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountLifecycleStatus lifecycleStatus;

    @NotNull
    @Column(name = "LOCK_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountLockStatus lockStatus;

    @NotNull
    @Column(name = "LAST_STATUS-CHANGED_AT", nullable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP_WITH_TIMEZONE)
    private Instant lastStatusChangeAt;

    @NotNull
    @Embedded
    private CredentialsEntity credentials;

    @NotNull
    @Embedded
    private EmailAddressEntity email;

    @ManyToMany
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<RoleEntity> roleEntities;

    public AccountEntity() {}
}