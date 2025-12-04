package com.authorization.server.identity;

import java.util.Objects;

/*
 * Value object that enforces the invariant correctness and compares the incoming password
 * during login attempt with the currently stored password in the system.
 */
public record Password (String password) {

    /*
     * Compact constructor to control and validate the password field.
     */
    public Password {
        Objects.requireNonNull(password, "field password cannot be null");
        if (password.isBlank()) {
            throw new IllegalArgumentException("field password cannot be blank");
        }
    }

    /*
     * Compares the incoming password at the login attempt with the password already
     * stored in the system.
     */
    Boolean matches(Password candidate) {
        if (candidate == null) {
            return false;
        }
        return this.password.equals(candidate.password);
    }
}