package com.authorization.server.infrastructure.persistence.jpa.entity.authorization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import com.authorization.server.infrastructure.persistence.jpa.entity.identity.AccountEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ROLE", uniqueConstraints = @UniqueConstraint(columnNames = "DISPLAY_NAME"))
@Getter
@Setter
@AllArgsConstructor
public class RoleEntity {

    private static final String AccountEntity_RoleEntities = "roleEntities";

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "DISPLAY_NAME", nullable = false)
    private String displayName;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissionEntities;

    public RoleEntity() {}

    private Boolean hasPermission(PermissionEntity permission) {
        return permissionEntities.contains(permission);
    }

    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissionEntities = permissions != null ? new HashSet<>(permissions) : new HashSet<>();
    }
}