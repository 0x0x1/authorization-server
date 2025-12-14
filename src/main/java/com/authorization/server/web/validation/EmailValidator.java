package com.authorization.server.web.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.authorization.server.web.validation.annotation.ValidateEmail;


/**
 * Custom validator implementing {@link ConstraintValidator} to validate email addresses.
 *
 * This validator uses a regular expression pattern to check whether the provided email string
 * conforms to a simple but commonly accepted email format:
 * - Starts with alphanumeric characters, plus signs, underscores, dots, or hyphens
 * - Contains an '@' symbol separating local and domain parts
 * - Domain part contains valid characters followed by a dot and a top-level domain with at least two letters
 *
 * This class is used in conjunction with the {@link ValidateEmail} annotation to provide
 * declarative email validation on fields or method parameters.
 */
public class EmailValidator implements ConstraintValidator<ValidateEmail, String> {

    /**
     * Regular expression pattern for validating email addresses.
     */
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    /**
     * Compiled pattern instance to improve performance by avoiding recompilation.
     */
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    /**
     * Validates whether the given email string is valid according to the defined regex pattern.
     *
     * @param username the email string to validate
     * @param context  the context in which the constraint is evaluated (not used here)
     * @return {@code true} if the email matches the pattern; {@code false} otherwise
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return validateEmail(username);
    }

    /**
     * Helper method that performs the regex matching against the email pattern.
     *
     * @param email the email string to validate
     * @return {@code true} if the email matches the pattern; {@code false} otherwise
     */
    private boolean validateEmail(String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
}