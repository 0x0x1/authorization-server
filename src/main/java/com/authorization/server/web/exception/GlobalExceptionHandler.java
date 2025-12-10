package com.authorization.server.web.exception;

import java.util.Locale;

import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.authorization.server.application.exception.DuplicateUserException;
import com.authorization.server.identity.constant.Domain;
import com.authorization.server.web.api.response.ApiResponseHandler;
import com.authorization.server.web.api.response.I18n;
import com.authorization.server.web.api.response.ErrorContextWithLocale;
import com.authorization.server.web.api.response.Result;
import com.authorization.server.common.validation.ValidationContext;
import com.authorization.server.web.constant.Web;

/**
 * Global exception handler for the web security microservice.
 *
 * This class is annotated with Spring's {@link ControllerAdvice} to intercept
 * and process exceptions thrown across the application controllers. It provides
 * consistent, localized HTTP responses for known exceptions, encapsulating
 * error details in a structured {@link Result} object wrapped inside a
 * {@link ResponseEntity} with an appropriate HTTP status code.
 *
 * Localization of error messages is performed using {@link I18n}, which
 * retrieves messages based on the provided {@link Locale}. This ensures that error
 * messages and severities are returned in the correct language and format for
 * the client.
 *
 * Exception handling methods specifically cover:
 *
 *   {@link DuplicateUserException}: Returns a 400 Bad Request with a warning severity message.
 *   {@link MethodArgumentNotValidException}: Returns a 400 Bad Request with an info severity message.
 *
 * Additional exception types can be added similarly.
 *
 * Null-safe locale handling is provided to default to the system locale if
 * no locale is explicitly supplied, ensuring messages are always localized properly.
 *
 * This class depends on {@link ApiResponseHandler} to create
 * {@link ErrorContextWithLocale} instances which encapsulate error details,
 * HTTP status, severity, and locale, allowing a clean separation of concerns.
 *
 *
 * @see ControllerAdvice
 * @see ExceptionHandler
 * @see ResponseEntity
 * @see Result
 * @see I18n
 * @see ApiResponseHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Utility to fetch localized messages from message source based on keys and locale.
     * This allows error messages and severities to be internationalized.
     */
    private final I18n i18n;

    /**
     * Constructs the GlobalExceptionProcessor with the required message utility.
     *
     * @param i18n utility for resolving localized messages from resource bundles.
     */
    public GlobalExceptionHandler(I18n i18n) {
        this.i18n = i18n;
    }

    /**
     * Handles DuplicateUserException by returning a localized error response with severity WARNING.
     *
     * This method creates a localized error context for the duplicate user error,
     * specifying the failed action, reason, severity, HTTP status, and locale.
     * It then delegates to {@link #ErrorResponseWithLocale(ErrorContextWithLocale)} to build
     * the full HTTP response with the appropriate error payload.
     *
     * @param locale the locale of the client for localized messages; if null, system default is used
     * @return ResponseEntity containing the error result with HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Result<?>> handleDuplicateUserException(Locale locale) {
        var ctx = ApiResponseHandler.createErrorContextWithLocaleOnFailure(
                            Web.AccountFlow.ACCOUNT_SIGN_UP_FAILED,
                            Domain.Validation.DUPLICATES_NOT_ALLOWED,
                            Web.MessageSeverity.WARNING,
                            HttpStatus.BAD_REQUEST,
                            getSafeLocale(locale));
        return ErrorResponseWithLocale(ctx);
    }

    /**
     * Handles MethodArgumentNotValidException by returning a localized error response with severity INFO.
     *
     * This exception typically occurs when validation on method arguments fails.
     * It constructs the error context with validation failure messages and passes it
     * to the common error response method.
     *
     * @param locale the locale for message localization; falls back to default if null.
     * @return ResponseEntity with status 400 and localized validation error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> handleValidationException(Locale locale) {
        var ctx = ApiResponseHandler.createErrorContextWithLocaleOnFailure(
                            Domain.Validation.ACCOUNT_VALIDATION_FAILED,
                            Domain.Validation.ACCOUNT_EMAIL_ADDRESS_INVALID,
                            Web.MessageSeverity.INFO,
                HttpStatus.BAD_REQUEST,
                            getSafeLocale(locale));

        return ErrorResponseWithLocale(ctx);
    }

    @ExceptionHandler(ApiRequestLimitException.class)
    public ResponseEntity<Result<?>> processRequestLimitException(Locale locale) {
        var ctx = ApiResponseHandler.createErrorContextWithLocaleOnFailure(
                        Web.Resilience4j.USER_REGISTRATION_FAILED,
                        Web.Resilience4j.API_REQUESTS_EXCEED_THRESHOLD,
                        Web.MessageSeverity.INFO,
                        HttpStatus.TOO_MANY_REQUESTS,
                        getSafeLocale(locale));

        return ErrorResponseWithLocale(ctx);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Result<?>> handleDisabledUserException(Locale locale) {
        var ctx = ApiResponseHandler.createErrorContextWithLocaleOnFailure(
                        Web.AccountFlow.ACCOUNT_LOGIN_FAILED,
                        Web.AccountFlow.ACCOUNT_DISABLED,
                        Web.MessageSeverity.WARNING,
                        HttpStatus.UNAUTHORIZED,
                        getSafeLocale(locale)
        );

        return ErrorResponseWithLocale(ctx);
    }

    /**
     * Constructs a ResponseEntity containing the localized error result based on the provided context.
     *
     * Retrieves localized messages for the failed action, reason, and severity.
     * Wraps these messages inside a MessageList and creates a Result object
     * with the configured HTTP status code.
     *
     * @param ctx context containing all necessary information for building the error response.
     * @return ResponseEntity wrapping the error Result with the appropriate HTTP status.
     */
    private ResponseEntity<Result<?>> ErrorResponseWithLocale(ErrorContextWithLocale ctx) {
        String failedAction = i18n.getMessage(ctx.getFailedAction(), ctx.getLocale());
        String reasonForFailedAction = i18n.getMessage(ctx.getReason(), ctx.getLocale());
        String errorSeverity = i18n.getMessage(ctx.getSeverity(), ctx.getLocale());

        var validationContext = new ValidationContext(failedAction);
        validationContext.addError(errorSeverity, reasonForFailedAction);

        var result = Result
                .status(ctx.getHttpStatus())
                .message(failedAction)
                .errors(validationContext).build();

        return ResponseEntity
                .status(ctx.getHttpStatus())
                .body(result);
    }

    /**
     * Returns a non-null Locale instance.
     *
     * If the provided locale is null, this method returns the system default locale.
     * to ensure message localization always has a valid locale.
     *
     * @param locale the input locale, possibly null.
     * @return the input locale if not null; otherwise, the default system locale.
     */
    @NotNull
    private Locale getSafeLocale(Locale locale) {
        return (locale != null) ? locale : Locale.getDefault();
    }
}