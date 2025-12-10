package com.authorization.server.web.api.response;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;

/**
 * Encapsulates all contextual information needed to generate a localized error response.
 *
 * This class holds key details such as the action that failed, the reason for failure,
 * the HTTP status code to return, the severity level of the error, and the locale for
 * message localization.
 *
 * Validation checks ensure mandatory fields are never set to null, enforcing
 * data integrity for reliable error processing.
 */

@Getter
@Setter
public class ErrorContextWithLocale {

    /**
     * The key identifying the failed action, used for localization of the error message.
     * Cannot be null.
     */
    private String failedAction;

    /**
     * The key indicating the reason for the failure, used for localization.
     * Cannot be null.
     */
    private String reason;

    /**
     * The HTTP status code to be returned in the response (e.g., 400 for bad request).
     */
    private HttpStatus httpStatus;

    /**
     * The severity level of the error (e.g., "warning", "error", "info") as a localized string.
     * Cannot be null.
     */
    private String severity;

    /**
     * The locale to use for localizing messages.
     * Cannot be null.
     */
    private Locale locale;

    /**
     * Default no-argument constructor.
     * Allows constructing an instance to be populated via setters or a builder.
     */
    public ErrorContextWithLocale() {
    }

    /**
     * Sets the failed action key.
     *
     * @param failedAction the key representing the failed action; must not be null
     * @throws IllegalArgumentException if failedAction is null
     */
    public void setFailedAction(String failedAction) {
        Assert.notNull(failedAction, "failedAction must not be null");
        this.failedAction = failedAction;
    }

    /**
     * Sets the failure reason key.
     *
     * @param reason the reason key; must not be null
     * @throws IllegalArgumentException if reason is null
     */
    public void setReason(String reason) {
        Assert.notNull(reason, "reason must not be null");
        this.reason = reason;
    }

    /**
     * Returns the severity level as a localized string.
     *
     * @return the severity string; never null if properly set
     * @throws IllegalArgumentException if severity is null when accessed
     */
    public String getSeverity() {
        Assert.notNull(severity, "severity must not be null");
        return severity;
    }

    /**
     * Sets the severity level for this error context.
     *
     * @param severity the severity string (e.g., "warning", "error", "info"); must not be null
     * @throws IllegalArgumentException if severity is null
     */
    public void setSeverity(String severity) {
        Assert.notNull(severity, "severity must not be null");
        this.severity = severity;
    }

    /**
     * Sets the locale for message localization.
     *
     * @param locale the locale to use; must not be null
     * @throws IllegalArgumentException if locale is null
     */
    public void setLocale(Locale locale) {
        Assert.notNull(locale, "locale must not be null");
        this.locale = locale;
    }
}