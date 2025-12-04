package com.authorization.server.identity;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Value Object that enforces the correctness of the invariant.
 */
public record EmailAddress (String emailAddress) {

    /**
     * Regular expression pattern for validating email addresses.
     */
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    /**
     * Compiled pattern instance to improve performance by avoiding recompilation.
     */
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    /**
     * Compact constructor to control and validate the email address.
     */
    public EmailAddress {
        Objects.requireNonNull(emailAddress, "field emailAddress cannot be null");

        if (emailAddress.isBlank()) {
            throw new IllegalArgumentException("field emailAddress cannot be blank");
        }

        String normalized = emailAddress.trim().toLowerCase();

        Matcher emailAddressPattern = PATTERN.matcher(normalized);

        if (!emailAddressPattern.matches()) {
            throw new IllegalArgumentException("Invalid email address");
        }

        emailAddress = normalized;
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}