package com.authorization.server.core.constant;

public class ApiConstants {

    private ApiConstants() {
        throw new IllegalStateException("Utility class");
    }

    /** Base path for all authentication-related REST endpoints. */
    public static final String BASE_PATH = "/rest/auth";

    /** Endpoint path for registering a new user. */
    public static final String REGISTRATION_PATH = "/public/registerAccount";

    /** Endpoint path for verifying a new user. */
    public static final String VERIFICATION_PATH = "/public/verifyEmailUser/{activationToken}";

    /** Endpoint path for login attempt. */
    public static final String LOGIN_PATH = "/public/loginUser";

    /** configuration that identifies Resilience4j instance. */
    public static final String USER_REGISTRATION_SERVICE = "user-registration-service";

}
