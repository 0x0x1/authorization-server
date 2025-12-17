package com.authorization.server.web.exception;

import java.util.Locale;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.authorization.server.infrastructure.persistence.DuplicateAccountException;
import com.authorization.server.infrastructure.persistence.constant.Jpa;
import com.authorization.server.web.api.response.I18n;
import com.authorization.server.web.api.response.Result;
import com.authorization.server.web.constant.Web;
import com.authorization.server.common.ApiUtil;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final I18n i18n;

    public GlobalExceptionHandler(I18n i18n) {
        this.i18n = i18n;
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<Result<?>> handleDuplicateUserException(DuplicateAccountException ex, Locale locale) {

        String message = null;

        if (Jpa.Column.EMAIL_CONSTRAINT_NAME.equalsIgnoreCase(ex.getContraintName())){
            message = i18n.getMessage(Web.AccountFlow.REGISTRATION_WITH_DUPLICATE_EMAIL, ApiUtil.getSafeLocale(locale));
        }

        if (Jpa.Column.USERNAME_CONSTRAINT_NAME.equalsIgnoreCase(ex.getContraintName())){
            message = i18n.getMessage(Web.AccountFlow.REGISTRATION_WITH_DUPLICATE_USERNAME, ApiUtil.getSafeLocale(locale));
        }

        var result = Result.onFailure()
                .withCode(HttpStatusCode.valueOf(409).value())
                .withMessage(message)
                .build();

        return ResponseEntity.status(result.code()).body(result);
    }

//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<Result<?>> handleValidationException(ValidationException ex, Locale locale) {
//        String message = i18n.getMessage(Domain.Validation.ACCOUNT_VALIDATION_FAILED, ApiUtil.getSafeLocale(locale));
//
//        var result = Result.onFailure()
//                .withCode(HttpStatus.BAD_REQUEST)
//                .withMessage(message)
//                .withErrors(ex.getErrors())
//                .build();
//
//        return ResponseEntity.status(result.code()).body(result);
//    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Result<?>> processRequestLimitException(Locale locale) {
        String message = i18n.getMessage(Web.Resilience4j.API_REQUESTS_EXCEED_THRESHOLD, ApiUtil.getSafeLocale(locale));

        var result = Result.onFailure()
                .withCode(HttpStatusCode.valueOf(429).value())
                .withMessage(message)
                .build();

        return ResponseEntity.status(result.code()).body(result);
    }

//    @ExceptionHandler(DisabledException.class)
//    public ResponseEntity<Result<?>> handleDisabledUserException(Locale locale) {
//        String message = i18n.getMessage(Web.AccountFlow.ACCOUNT_DISABLED, ApiUtil.getSafeLocale(locale));
//
//        var result = Result.onFailure()
//                .withCode(HttpStatus.NOT_ACCEPTABLE)
//                .withMessage(message)
//                .build();
//
//        return ResponseEntity.status(result.code()).body(result);
//    }

//    @ExceptionHandler(AccountContraintViolationException.class)
//    public ResponseEntity<Result<?>> handleAccountContraintViolationException(Locale locale) {
//        String message = i18n.getMessage(Web.AccountFlow.ACCOUNT_DISABLED, ApiUtil.getSafeLocale(locale));
//
//        var result = Result.onFailure()
//                .withCode(HttpStatus.NOT_ACCEPTABLE)
//                .withMessage(message)
//                .build();
//
//        return ResponseEntity.status(result.code()).body(result);
//    }
}