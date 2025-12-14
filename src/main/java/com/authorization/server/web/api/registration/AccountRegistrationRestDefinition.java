package com.authorization.server.web.api.registration;

import java.util.Locale;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import com.authorization.server.web.api.response.Result;
import com.authorization.server.web.dto.RegisterRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Open api documentation For Account REST-API.
 */
public interface AccountRegistrationRestDefinition {

    /**
     * Registers a new user based on the provided registration data.
     *
     * @param requestDto the registration request payload containing user data.
     * @param locale the locale used for localizing the response messages (e.g., accept-language: de-DE in request header)
     * @return a {@link ResponseEntity} containing a {@link Result} object that holds
     *              the registration response and appropriate messages
     */
    @Tag(name = "Accounts", description = "User account management")
    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "Cannot process the request"),
            @ApiResponse(responseCode = "409", description = "Internal conflict"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    ResponseEntity<Result<?>> registerAccount(@RequestBody RegisterRequestDto requestDto, BindingResult bindingResult, Locale locale);
}