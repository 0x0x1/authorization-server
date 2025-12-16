package com.authorization.server.web.exception;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.authorization.server.application.exception.ValidationException;
import com.authorization.server.application.exception.DuplicateUserException;
import com.authorization.server.domain.constant.Domain;
import com.authorization.server.web.api.response.I18n;
import com.authorization.server.web.api.response.Result;
import com.authorization.server.web.constant.Web;
import com.authorization.server.common.ApiUtil;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final I18n i18n;

    public GlobalExceptionHandler(I18n i18n) {
        this.i18n = i18n;
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Result<?>> handleDuplicateUserException(Locale locale) {
        String message = i18n.getMessage(Web.AccountFlow.ACCOUNT_REGISTRATION_FAILED, ApiUtil.getSafeLocale(locale));

        var result = Result.onFailure()
                .withCode(HttpStatus.BAD_REQUEST)
                .withMessage(message)
                .build();

        return ResponseEntity.status(result.code()).body(result);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Result<?>> handleValidationException(ValidationException ex, Locale locale) {
        String message = i18n.getMessage(Domain.Validation.ACCOUNT_VALIDATION_FAILED, ApiUtil.getSafeLocale(locale));

        var result = Result.onFailure()
                .withCode(HttpStatus.BAD_REQUEST)
                .withMessage(message)
                .withErrors(ex.getErrors())
                .build();

        return ResponseEntity.status(result.code()).body(result);
    }

    @ExceptionHandler(ApiRequestLimitException.class)
    public ResponseEntity<Result<?>> processRequestLimitException(Locale locale) {
        String message = i18n.getMessage(Web.Resilience4j.API_REQUESTS_EXCEED_THRESHOLD, ApiUtil.getSafeLocale(locale));

        var result = Result.onFailure()
                .withCode(HttpStatus.TOO_MANY_REQUESTS)
                .withMessage(message)
                .build();

        return ResponseEntity.status(result.code()).body(result);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Result<?>> handleDisabledUserException(Locale locale) {
        String message = i18n.getMessage(Web.AccountFlow.ACCOUNT_DISABLED, ApiUtil.getSafeLocale(locale));

        var result = Result.onFailure()
                .withCode(HttpStatus.NOT_ACCEPTABLE)
                .withMessage(message)
                .build();

        return ResponseEntity.status(result.code()).body(result);
    }
}