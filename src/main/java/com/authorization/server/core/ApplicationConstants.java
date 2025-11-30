package com.authorization.server.core;

/**
 * A utility class that holds application-wide constant values.
 *
 * This class contains string constants for message keys used in localization,
 * as well as HTTP status codes and message severity types.
 *
 * The class is final and has a private constructor to prevent instantiation.
 */
public final class ApplicationConstants {

    private ApplicationConstants() {
        throw new IllegalStateException("Utility class");
    }

    /** Message key for successful user sign-up. */
    public final static String SIGN_UP_SUCCESS = "app.signup.success";

    /** Message key for successful user account activation. */
    public final static String ACCOUNT_ACTIVATION_SUCCESS = "app.account.activation.success";

    /** Message key for failed user sign-up. */
    public final static String SIGN_UP_FAILED = "app.signup.failure";

    /** Message key for successful user login. */
    public final static String LOGIN_SUCCESS = "app.login.success";

    /** Message key indicating duplicates are not allowed. */
    public final static String DUPLICATES_NOT_ALLOWED = "app.duplicates.not.allowed";

    /** Message key for validation failure. */
    public final static String VALIDATION_FAILED = "app.validation.failed";

    /** Message key for api requests failure. */
    public final static String USER_REGISTRATION_FAILED = "app.registration.failed";

    /** Message key for email validation failure. */
    public final static String INVALID_EMAIL = "app.invalid.email";

    public final static String API_REQUESTS_EXCEED_THRESHOLD = "app.requests.exceed.threshold";

    public final static String USER_DISABLED = "app.user.disabled";

    public final static String SUBJECT = "app.verify.your.email";

    public final static String USER_LOGIN_FAILED = "app.user.login.failed";







    /** HTTP status code for resource creation success. */
    public final static int CREATED = 201;

    /** HTTP status code for bad request. */
    public final static int BAD_REQUEST = 400;

    /** HTTP status code for too many requests. */
    public final static int TOO_MANY_REQUESTS = 429;

    /** HTTP status code for general success. */
    public final static int SUCCESS = 200;

    public final static int UNAUTHORIZED = 401;

    /** HTTP status code for internal server error. */
    public final static int INTERNAL_SERVER_ERROR = 500;
}