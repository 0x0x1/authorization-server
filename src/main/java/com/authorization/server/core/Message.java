package com.authorization.server.core;

import org.springframework.util.Assert;

import com.authorization.server.core.validation.ValidationContext;

/**
 * Represents a simple message containing a severity and a textual description.
 *
 * Message severity typically indicate the nature or severity of the message, such as
 * {@code "warning"}, {@code "error"}, or {@code "info"}.
 * The text provides human-readable details about the message.
 *
 *
 * This class is often used in validation or processing workflows to
 * report issues or informative notes to the user or calling system.
 *
 * @see ValidationContext
 */
public class Message {

    /**
     * The severity of the message (e.g., "warning", "error", "info").
     */
    private String severity;

    /**
     * The textual content of the message (e.g., "Invalid email address").
     */
    private String text;

    /**
     * Constructs a new Message instance with the specified severity and text.
     *
     * @param severity the severity of the message; must not be {@code null}.
     * @param text the text of the message; must not be {@code null}.
     * @throws IllegalArgumentException if {@code severity} or {@code text} is {@code null}.
     */
    public Message(String severity, String text) {
        Assert.notNull(severity, "message severity must not be null");
        Assert.notNull(text, "message text must not be null");
        this.severity = severity;
        this.text = text;
    }

    /**
     * Returns the severity of the message.
     *
     * @return the message severity.
     */
    public String getSeverity() {
        return this.severity;
    }

    /**
     * Returns the textual content of the message.
     *
     * @return the message text.
     */
    public String getText() {
        return this.text;
    }
}