package com.authorization.server.web.api.response;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Utility class for retrieving localized messages from the application's message source.
 *
 * This component acts as a wrapper around Spring's {@link MessageSource}, simplifying
 * the access to localized strings using message codes and locale information.
 */
@Component
public class I18n {

    private final MessageSource messageSource;

    /**
     * Constructs a new {@code MessageUtil} with the provided {@link MessageSource}.
     *
     * @param messageSource the message source used to resolve localized messages
     */
    public I18n(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Retrieves a localized message by its code and locale.
     *
     * @param code   the message code to look up, (e.g., 'user.signup.success')
     * @param locale the locale to use for message resolution (e.g., passing Accept-language: de-DE in request header)
     * @return the resolved message as a {@link String}
     */
    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }
}