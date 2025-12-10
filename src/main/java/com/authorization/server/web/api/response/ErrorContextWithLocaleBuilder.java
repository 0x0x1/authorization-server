package com.authorization.server.web.api.response;

import java.util.Locale;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

/**
 * Builder class for creating instances of {@link ErrorContextWithLocale} in a
 * fluent and safe manner.
 *
 * This builder enforces non-null constraints on mandatory fields during
 * the build process using Spring's {@link Assert} utility, ensuring that
 * all required data is provided before constructing a {@code LocalizedErrorContext}.
 *
 */
public class ErrorContextWithLocaleBuilder {

    private String failedAction;
    private String reason;
    private String severity;
    private HttpStatus httpStatus;
    private Locale locale;

    /**
     * Sets the failed action key describing the failure context.
     *
     * @param failedAction the key representing the failed action; must not be null.
     * @return this builder instance for method chaining.
     * @throws IllegalArgumentException if failedAction is null.
     */
    public ErrorContextWithLocaleBuilder withFailedAction(String failedAction) {
        Objects.requireNonNull(failedAction, "failedAction must not be null");
        this.failedAction = failedAction;
        return this;
    }

    /**
     * Sets the reason key explaining the cause of failure.
     *
     * @param reason the key representing the failure reason; must not be null.
     * @return this builder instance for method chaining.
     * @throws IllegalArgumentException if reason is null.
     */
    public ErrorContextWithLocaleBuilder withReason(String reason) {
        Assert.notNull(reason, "reason must not be null");
        this.reason = reason;
        return this;
    }

    /**
     * Sets the severity of the error as a localized string (e.g., "warning", "error").
     *
     * @param severity the severity string; must not be null.
     * @return this builder instance for method chaining.
     * @throws IllegalArgumentException if severity is null.
     */
    public ErrorContextWithLocaleBuilder withSeverity(String severity) {
        Assert.notNull(severity, "severity must not be null");
        this.severity = severity;
        return this;
    }

    /**
     * Sets the HTTP status code associated with this error.
     *
     * @param httpStatus the HTTP status code (e.g., 400, 404).
     * @return this builder instance for method chaining.
     */
    public ErrorContextWithLocaleBuilder withHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    /**
     * Sets the locale to be used for localizing error messages.
     *
     * @param locale the {@link Locale}; must not be null.
     * @return this builder instance for method chaining.
     * @throws IllegalArgumentException if locale is null.
     */
    public ErrorContextWithLocaleBuilder withLocale(Locale locale) {
        Assert.notNull(locale, "locale must not be null");
        this.locale = locale;
        return this;
    }

    /**
     * Builds a {@link ErrorContextWithLocale} instance with the configured properties.
     *
     * This method will call the setters on {@code LocalizedErrorContext}, which
     * perform null checks, so this builder assumes all required properties have.
     * been set.
     *
     * @return a fully constructed {@link ErrorContextWithLocale} instance.
     * @throws IllegalArgumentException if any required property is missing or null.
     */
    public ErrorContextWithLocale build() {
        final ErrorContextWithLocale errorContext = new ErrorContextWithLocale();
        errorContext.setFailedAction(failedAction);
        errorContext.setReason(reason);
        errorContext.setSeverity(severity);
        errorContext.setHttpStatus(httpStatus);
        errorContext.setLocale(locale);
        return errorContext;
    }
}