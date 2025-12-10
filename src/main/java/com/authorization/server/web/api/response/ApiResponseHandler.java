package com.authorization.server.web.api.response;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.authorization.server.common.validation.ValidationContext;

/**
 * Utility class providing helper methods for transforming API-related objects,
 * such as simplifying or flattening message structures for easier consumption.
 */
public class ApiResponseHandler {

    private ApiResponseHandler() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Utility method to flatten the errors for simplified consumption.
     *
     * @param validationContext
     * @return flatten errors.
     */
    public static List<Message> flattenErrors(ValidationContext validationContext) {
            if (validationContext == null || validationContext.getErrors() == null) {
                return Collections.emptyList();
            }
            return validationContext.getErrors();
        }

    /**
     * Creates a {@link ErrorContextWithLocale} instance using the provided parameters.
     *
     * This method acts as a convenient factory to construct a fully initialized
     * {@link ErrorContextWithLocale} by applying necessary validations on the input
     * parameters before building the context using the {@link ErrorContextWithLocaleBuilder}.
     *
     * The method validates that none of the critical parameters — {@code failedAction},
     * {@code reason}, {@code severity}, and {@code locale} — are {@code null}, throwing
     * an {@link IllegalArgumentException} if any validation fails.
     *
     * @param failedAction a message key representing the action that failed; must not be {@code null}
     * @param reason a message key describing the reason for failure; must not be {@code null}
     * @param severity a string indicating the severity level of the error (e.g., "warning", "error"); must not be {@code null}
     * @param httpStatus the HTTP status code associated with the error (e.g., 400, 500)
     * @param locale the {@link Locale} for message localization; must not be {@code null}
     * @return a fully constructed {@link ErrorContextWithLocale} with all provided properties set
     * @throws IllegalArgumentException if any of {@code failedAction}, {@code reason}, {@code severity}, or {@code locale} is {@code null}
     */
    public static ErrorContextWithLocale createErrorContextWithLocaleOnFailure(String failedAction, String reason,
                                                                               String severity, HttpStatus httpStatus, Locale locale) {
        Objects.requireNonNull(failedAction, "failedAction must not be null");
        Objects.requireNonNull(reason, "reason must not be null");
        Objects.requireNonNull(severity, "severity must not be null");
        Objects.requireNonNull(locale, "locale must not be null");

        return new ErrorContextWithLocaleBuilder()
                .withFailedAction(failedAction)
                .withReason(reason)
                .withSeverity(severity)
                .withHttpStatus(httpStatus)
                .withLocale(locale)
                .build();
    }
}