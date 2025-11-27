package com.authorization.server.infrastructure.persistence.jpa.entity.account;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import com.authorization.server.domain.account.Permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class RoleTypeEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String roleTypeName;

    @ManyToMany
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissionEntities;

    public RoleTypeEntity() {

    }

    public RoleTypeEntity(Set<PermissionEntity> permissionEntities) {
        this.permissionEntities = permissionEntities;
    }

    private Boolean hasPermission(Permission permission) {
        return permissionEntities.contains(permission);
    }
}