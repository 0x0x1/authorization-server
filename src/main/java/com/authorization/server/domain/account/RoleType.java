package com.authorization.server.domain.account;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class RoleType {

    private String displayName;
    private final Set<Permission> permissions;

    private Boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

}
