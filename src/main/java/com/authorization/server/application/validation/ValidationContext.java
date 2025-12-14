package com.authorization.server.application.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.authorization.server.web.api.response.Message;

import lombok.Getter;

/**
 * A class that collects and manages {@link Message} objects categorized
 * by type (e.g., warning, error, info).
 *
 * This class is typically used to accumulate messages during validation
 * or processing and to check for errors before proceeding.
 *
 */
@Getter
public class ValidationContext {

    private final List<Message> errors;
    private final String context;

    /**
     * Constructs an empty {@code MessageList}.
     */
    public ValidationContext(String context) {
        this.context = context;
        errors = new ArrayList<>();
    }

    /**
     * Adds a new message with the specified severity and text.
     *
     * @param severity the severity of the message (e.g., "error", "warning", "info").
     * @param text the content of the message.
     */
    private void addMessage(String severity, String text) {
        errors.add(new Message(severity, text));
    }

    /**
     * Adds a warning message.
     *
     * @param text the content of the warning.
     */
    public void addError(String severity, String text) {
        addMessage(severity, text);
    }

    /**
     * Checks whether this list contains any error messages.
     *
     * @return {@code true} if at least one message has severity "error"; otherwise {@code false}.
     */
    public boolean hasErrors() {
        return errors.stream().findAny().isPresent();
    }

    /**
     * @return the list of error messages.
     * Returns empty list if no messages are present.
     */
    public List<Message> getErrors() {
      if (hasErrors()) {
          return errors;
      }
      return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "ValidationContext{" +
                "errors=" + errors +
                ", errors='" + context + '\'' +
                '}';
    }
}