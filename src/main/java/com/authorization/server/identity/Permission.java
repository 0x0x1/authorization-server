package com.authorization.server.identity;

import java.util.Objects;

/*
 * Represent the permission a logged-in user has.
 */
public record Permission (String displayName, String description) {

    /*
     * Compact constructor enforcing rules.
     */
    public Permission {
        Objects.requireNonNull(displayName, "field displayName cannot be null");
        Objects.requireNonNull(description, "field description cannot be null");

        if (displayName.isBlank() || description.isBlank()) {
            throw new IllegalArgumentException("displayName and description cannot be blank");
        }

        displayName = displayName.trim();
        description = description.trim();
    }

    /*
     * Two Permissions are equal only by their display name.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Permission that = (Permission) object;
        return displayName.equals(that.displayName);
    }

    @Override
    public int hashCode() {
        return displayName.hashCode();
    }
}