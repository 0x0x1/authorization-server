package com.authorization.server.domain.account;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    private String permissionName;
    private String description;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(permissionName, that.permissionName);  // Compare by name only
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionName);  // Hash by name only
    }
}
