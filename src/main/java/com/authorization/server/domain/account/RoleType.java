package com.authorization.server.domain.account;

import java.util.Set;

public enum RoleType {
    ROLE_USER(Set.of(Permission.READ_PERMISSION)),
    ROLE_ADMIN(Set.of(Permission.READ_PERMISSION, Permission.WRITE_PERMISSION, Permission.DELETE_PERMISSION));

    private final Set<Permission> permissions;

    RoleType(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public Boolean supportPasswordReset() {
        return this.equals(ROLE_ADMIN);
    }
}