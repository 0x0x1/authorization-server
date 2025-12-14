package com.authorization.server.common;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import jakarta.validation.constraints.NotNull;

import com.authorization.server.application.validation.ValidationContext;
import com.authorization.server.web.api.response.Message;

public class ApiUtil {

    private ApiUtil() {
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
     * Returns a non-null Locale instance.
     *
     * If the provided locale is null, this method returns the system default locale.
     * to ensure message localization always has a valid locale.
     *
     * @param locale the input locale, possibly null.
     * @return the input locale if not null; otherwise, the default system locale.
     */
    @NotNull
    public static Locale getSafeLocale(Locale locale) {
        return (locale != null) ? locale : Locale.getDefault();
    }

}
