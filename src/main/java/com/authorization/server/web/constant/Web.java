package com.authorization.server.web.constant;

/**
 * Centralized collection of API-related constants used across the web layer.
 * <p>
 * This class organizes URL routes, status codes, message keys, security headers,
 * and Resilience4j identifiers into domain-specific nested groups. It provides
 * a single, structured source of truth for values shared across controllers,
 * filters, exception handlers, and service integration points.
 * </p>
 *
 * <p>
 * All nested classes are utility-only and cannot be instantiated.
 * </p>
 */
public class Web {

    private Web() {}

    /**
     * Contains URL path constants for REST endpoints exposed by the authentication API.
     * These values define the structure of public and protected HTTP routes.
     */
    public static final class Route {
        private Route() {
            throw new IllegalStateException("Utility class");
        }

        /** Base path for all authentication-related REST endpoints. */
        public static final String BASE_PATH = "/rest/auth";

        /** Endpoint path for registering a new user account. */
        public static final String REGISTRATION_PATH = "/public/registerAccount";

        /** Endpoint path for verifying a new user via activation token. */
        public static final String VERIFICATION_PATH = "/public/verifyEmailUser/{activationToken}";

        /** Endpoint path for user login attempts. */
        public static final String LOGIN_PATH = "/public/loginUser";
    }

    /**
     * Contains message keys used for representing outcomes of account-related flows
     * such as registration, verification, and login.
     * <p>
     * These keys are typically used with the application's localization or messaging system.
     * </p>
     */
    public static final class AccountFlow {
        private AccountFlow() {
            throw new IllegalStateException("Utility class");
        }

        /** Message key for successful user sign-up. */
        public static final String SIGN_UP_SUCCESS = "app.signup.success";

        /** Message key for successful login. */
        public static final String LOGIN_SUCCESS = "app.login.success";

        /** Message key for successful user account activation. */
        public static final String ACCOUNT_ACTIVATION_SUCCESS = "app.account.activation.success";

        /** Message key for failed user sign-up. */
        public static final String ACCOUNT_SIGN_UP_FAILED = "app.signup.failure";

        public static final String ACCOUNT_LOGIN_FAILED = "app.login.failure";


        /** Message key indicating that a user account is disabled. */
        public static final String ACCOUNT_DISABLED = "app.user.disabled";

        /** Subject line for email verification messages. */
        public static final String SUBJECT = "app.verify.your.email";

        public static final String REGISTER_ACCOUNT_ATTEMPT = "app.register.account.attempt";
    }

    /**
     * Contains security-related constants used for authentication and authorization.
     */
    public static final class Security {
        private Security() {
            throw new IllegalStateException("Utility class");
        }

        /** HTTP header used to transmit authentication tokens. */
        public static final String AUTHORIZATION_HEADER = "Authorization";
    }

    /**
     * Contains raw integer values mapping to standard HTTP status codes.
     * These constants provide clarity and help avoid magic numbers.
     */
    public static final class HttpStatus {

        private HttpStatus() {
            throw new IllegalStateException("Utility class");
        }

        /** HTTP status code for successful resource creation. */
        public static final int CREATED = 201;

        /** HTTP status code indicating a bad request. */
        public static final int BAD_REQUEST = 400;

        /** HTTP status code indicating too many requests (rate limit reached). */
        public static final int TOO_MANY_REQUESTS = 429;

        /** HTTP status code for general success. */
        public static final int SUCCESS = 200;

        /** HTTP status code for unauthorized access. */
        public static final int UNAUTHORIZED = 401;

        /** HTTP status code for internal server errors. */
        public static final int INTERNAL_SERVER_ERROR = 500;
    }

    /**
     * Contains common HTTP header names used throughout the application.
     */
    public static final class Header {
        private Header() {}

        /** Authorization header typically containing a JWT or other token. */
        public static final String AUTHORIZATION = "Authorization";
    }

    /**
     * Contains identifiers and message keys used in conjunction with Resilience4j.
     * <p>
     * These constants represent circuit breaker instance names, tracked events,
     * error keys, and rate-limiting indicators.
     * </p>
     */
    public static final class Resilience4j {
        private Resilience4j() {
            throw new IllegalStateException("Utility class");
        }

        /** Circuit breaker instance name related to user registration. */
        public static final String USER_REGISTRATION_SERVICE = "user-registration-service";

        /** Message key used when user registration fails. */
        public static final String USER_REGISTRATION_FAILED = "user-registration-failed";

        /** Metric/event key representing a user registration attempt. */
        public static final String REGISTER_ACCOUNT_ATTEMPT = "register-account-attempt";

        /** Message key indicating API rate limits have been exceeded. */
        public static final String API_REQUESTS_EXCEED_THRESHOLD = "api-requests-exceed-threshold";

        /** Message key for validation failures intercepted in resilient flows. */
        public static final String VALIDATION_FAILED = "validation-failed";

        /** Message key representing invalid email input. */
        public static final String INVALID_EMAIL = "invalid-email";
    }

    public static final class MessageSeverity {
        private MessageSeverity() {
            throw new IllegalStateException("Utility class");
        }

        /** Message severity level: warning. */
        public static final String WARNING = "app.error.severity.warning";

        /** Message severity level: error. */
        public static final String ERROR = "app.error.severity.error";

        /** Message severity level: informational message. */
        public static final String INFO = "app.error.severity.info";
    }

    public static final class Validation {
        private Validation() {
            throw new IllegalStateException("Utility class");
        }


    }
}
