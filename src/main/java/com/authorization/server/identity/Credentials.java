package com.authorization.server.identity;

import java.util.Objects;

/*
 * Groups the username and password as a single unit. May add additional capability.
 */
public record Credentials(Username username, Password password) {

    /*
     * Compact constructor enforcing valid username and password state.
     */
    public Credentials {
        Objects.requireNonNull(username, "field username cannot be null");
        Objects.requireNonNull(password, "field password cannot be null");
    }
}