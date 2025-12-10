package com.authorization.server.infrastructure.persistence.jpa.entity.authorization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import com.authorization.server.infrastructure.persistence.jpa.constant.Jpa;
import com.authorization.server.infrastructure.persistence.jpa.entity.NamedEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = Jpa.Table.ROLE, uniqueConstraints = @UniqueConstraint(columnNames = Jpa.Column.DISPLAY_NAME))
public class RoleEntity extends NamedEntity {

    @NotNull
    @ManyToMany
    @JoinTable(
            name = Jpa.JoinTable.ROLES_PERMISSIONS,
            joinColumns = @JoinColumn(name = Jpa.ForeignKey.ROLE_ID),
            inverseJoinColumns = @JoinColumn(name = Jpa.ForeignKey.PERMISSION_ID)
    )
    private Collection<PermissionEntity> permissionEntities;

    private Boolean hasPermission(PermissionEntity permission) {
        return permissionEntities.contains(permission);
    }

    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissionEntities = permissions != null ? new HashSet<>(permissions) : new HashSet<>();
    }
}