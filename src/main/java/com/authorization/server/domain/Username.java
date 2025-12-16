package com.authorization.server.domain;

import java.util.Objects;

/*
 * Represents the username of the currently signed-in application user. Enforces invariant.
 */
public record Username (String username) {

    /*
     * Compact constructor to control and validate the username field.
     */
    public Username {
        Objects.requireNonNull(username, "field username cannot be null");
        if (username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
    }

    /*
     * Compares the username passed during a login attempt with the username
     * stored in the system. Domain validation.
     */
    Boolean matches(Username candidate) {
        if (candidate == null) {
            return false;
        }
        return this.username.equals(candidate.username);
    }
}