package com.authorization.server.infrastructure.persistence.jpa.entity.identity;

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

import com.authorization.server.core.constant.EntityConstants;
import com.authorization.server.identity.AccountLifecycleStatus;
import com.authorization.server.identity.AccountLockStatus;
import com.authorization.server.infrastructure.persistence.jpa.entity.BaseEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = EntityConstants.ACCOUNT, uniqueConstraints = @UniqueConstraint(columnNames = EntityConstants.EMAIL_ADDRESS ))
public class AccountEntity extends BaseEntity {

    @NotNull
    @Column(name = EntityConstants.LIFECYCLE_STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountLifecycleStatus lifecycleStatus;

    @NotNull
    @Column(name = EntityConstants.LOCK_STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountLockStatus lockStatus;

    @NotNull
    @Column(name = EntityConstants.CREATED_AT, nullable = false)
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
            name = EntityConstants.ACCOUNTS_ROLES,
            joinColumns = @JoinColumn(name = EntityConstants.ACCOUNT_ID),
            inverseJoinColumns = @JoinColumn(name = EntityConstants.ROLE_ID)
    )
    private Collection<RoleEntity> roleEntities;
}