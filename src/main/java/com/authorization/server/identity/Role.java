package com.authorization.server.identity;

import java.util.Objects;
import java.util.Set;

/*
 * Represents the role of the logged in application user.
 */
public record Role(String displayName, Set<Permission> permissions) {

    /*
     * Compact constructor enforcing rules.
     */
    public Role {
        Objects.requireNonNull(displayName, "field displayName of role must not be null");
        Objects.requireNonNull(permissions, "field permissions of role must not be null");
        if (displayName.isBlank()) {
            throw new IllegalArgumentException("displayName of role cannot be blank");
        }

        permissions.forEach(Objects::requireNonNull);
        permissions = Set.copyOf(permissions);

        displayName = displayName.trim().toLowerCase();
    }

    /*
     * Two Roles are equal only by their display name.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Role role = (Role) object;
        return displayName.equals(role.displayName);
    }

    @Override
    public int hashCode() {
        return displayName.hashCode();
    }
}