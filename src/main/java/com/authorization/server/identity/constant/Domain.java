package com.authorization.server.identity.constant;

public final class Domain {

    public final static class Validation {
        private Validation() {
            throw new IllegalStateException("Utility class");
        }

        /** Message key indicating that duplicate records are not allowed. */
        public static final String DUPLICATES_NOT_ALLOWED = "app.duplicates.not.allowed";

        public static final String INVALID_CREDENTIALS = "app.invalid.credentials";

        public static final String ACCOUNT_VALIDATION_FAILED = "app.account.validation.failed";

        public static final String ACCOUNT_EMAIL_ADDRESS_INVALID = "app.account.email.address.invalid";

        public static final String COMMAND_CANNOT_BE_NULL = "RegisterCommand cannot be null";
        public static final String ROLE_TYPE_REQUIRED = "At least one role type is required";
        public static final String USERNAME_REQUIRED = "The username is required";
        public static final String PASSWORD_REQUIRED = "The password is required";
        public static final String PASSWORD_INVALID = "The password must be at least 8 characters long and contain " +
                "at least one uppercase letter, one lowercase letter, one number and one special character";
        public static final String EMAIL_REQUIRED = "The email is required";
        public static final String EMAIL_INVALID = "The email is not valid";
    }
}
