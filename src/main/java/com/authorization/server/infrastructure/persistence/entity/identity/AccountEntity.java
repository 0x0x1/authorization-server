package com.authorization.server.infrastructure.persistence.entity.identity;

import java.time.Instant;
import java.util.Collection;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.authorization.server.domain.AccountLifecycleStatus;
import com.authorization.server.domain.AccountLockStatus;
import com.authorization.server.infrastructure.persistence.constant.Jpa;
import com.authorization.server.infrastructure.persistence.entity.BaseEntity;
import com.authorization.server.infrastructure.persistence.entity.authorization.RoleEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = Jpa.Table.ACCOUNT, uniqueConstraints = @UniqueConstraint(columnNames = Jpa.Column.EMAIL_ADDRESS ))
public class AccountEntity extends BaseEntity {

    @NotNull
    @Column(name = Jpa.Column.LIFECYCLE_STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountLifecycleStatus lifecycleStatus;

    @NotNull
    @Column(name = Jpa.Column.LOCK_STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountLockStatus lockStatus;

    @NotNull
    @Column(name = Jpa.Column.CREATED_AT, nullable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP_WITH_TIMEZONE)
    private Instant createdAt;

    @NotNull
    @Embedded
    private CredentialsEntity credentials;

    @NotNull
    @Embedded
    private EmailAddressEntity emailAddress;

    @ManyToMany
    @JoinTable(
            name = Jpa.JoinTable.ACCOUNTS_ROLES,
            joinColumns = @JoinColumn(name = Jpa.ForeignKey.ACCOUNT_ID),
            inverseJoinColumns = @JoinColumn(name = Jpa.ForeignKey.ROLE_ID)
    )
    private Collection<RoleEntity> roleEntities;
}