package com.authorization.server.infrastructure.web.api.contract;

import java.util.Locale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.authorization.server.infrastructure.web.helper.api.Result;
import com.authorization.server.infrastructure.web.payload.RegisterRequestDto;

public interface AccountRegistrationRestDefinition {

    /** Base path for all authentication-related REST endpoints. */
    String BASE_PATH = "/rest/auth";

    /** Endpoint path for registering a new user. */
    String REGISTRATION_PATH = "/public/registerUser";

    /** Endpoint path for verifying a new user. */
    String VERIFICATION_PATH = "/public/verifyEmailUser/{activationToken}";

    /** Endpoint path for login attempt. */
    String LOGIN_PATH = "/public/loginUser";

    /** configuration that identifies Resilience4j instance. */
    String USER_REGISTRATION_SERVICE = "user-registration-service";

    /**
     * Registers a new user based on the provided registration data.
     *
     * @param requestDto the registration request payload containing user details.
     * @param locale the locale used for localizing the response messages (e.g., accept-language: de-DE in request header)
     * @return a {@link ResponseEntity} containing a {@link Result} object that holds
     *              the registration outcome and appropriate messages
     */
    ResponseEntity<Result<?>> registerAccount(@RequestBody RegisterRequestDto requestDto, Locale locale);
}
