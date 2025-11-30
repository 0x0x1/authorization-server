package com.authorization.server.core;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.util.Assert;

import com.authorization.server.core.validation.ValidationContext;

/**
 * Utility class providing helper methods for transforming API-related objects,
 * such as simplifying or flattening message structures for easier consumption.
 */
public class ApiResponseProcessor {

    private ApiResponseProcessor() {
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
     * Creates a {@link LocalizedErrorContext} instance using the provided parameters.
     *
     * This method acts as a convenient factory to construct a fully initialized
     * {@link LocalizedErrorContext} by applying necessary validations on the input
     * parameters before building the context using the {@link LocalizedErrorContextBuilder}.
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
     * @return a fully constructed {@link LocalizedErrorContext} with all provided properties set
     * @throws IllegalArgumentException if any of {@code failedAction}, {@code reason}, {@code severity}, or {@code locale} is {@code null}
     */
    public static LocalizedErrorContext createLocalizedErrorContext(String failedAction, String reason,
                                                                    String severity, int httpStatus, Locale locale) {
        Assert.notNull(failedAction, "failedAction must not be null");
        Assert.notNull(reason, "reason must not be null");
        Assert.notNull(severity, "severity must not be null");
        Assert.notNull(locale, "locale must not be null");

        return new LocalizedErrorContextBuilder()
                .withFailedAction(failedAction)
                .withReason(reason)
                .withSeverity(severity)
                .withHttpStatus(httpStatus)
                .withLocale(locale)
                .build();
    }
}