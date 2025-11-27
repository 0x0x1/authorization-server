package com.authorization.server.domain.account;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleType {

    private final String roleTypeName;
    private final Set<Permission> permissions;

    private Boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public RoleType(String roleTypeName) {
        switch (roleTypeName.toUpperCase()) {
            case "ADMIN":
                this.roleTypeName = "ADMIN";
                this.permissions = Set.of(
                        new Permission("READ", "Can read all data"),
                        new Permission("WRITE", "Can write all data"),
                        new Permission("DELETE", "Can delete all data"));

                break;
            case "USER":
                this.roleTypeName = "USER";
                this.permissions = Set.of(
                        new Permission("READ", "Can read all data"));
                break;
            default:
                throw new IllegalArgumentException("Unknown role type: " + roleTypeName);
        }
    }

}
