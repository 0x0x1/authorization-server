package com.authorization.server.domain;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/*
 * Represents the role of the logged in application user.
 */
public record Role(UUID roleId, String displayName, String description, Collection<Permission> permissions) {

    /*
     * Compact constructor enforcing rules.
     */
    public Role {
        Objects.requireNonNull(roleId, "field roleId of role must not be null");
        Objects.requireNonNull(displayName, "field displayName of role must not be null");
        Objects.requireNonNull(description, "field description of role must not be null");
        Objects.requireNonNull(permissions, "field permissions of role must not be null");
        if (displayName.isBlank()) {
            throw new IllegalArgumentException("displayName of role cannot be blank");
        }

        if (description.isBlank()) {
            throw new IllegalArgumentException("description of role cannot be blank");
        }

        permissions.forEach(Objects::requireNonNull);
        permissions = Set.copyOf(permissions);

        displayName = displayName.trim().toLowerCase();
        description = description.trim().toLowerCase();
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