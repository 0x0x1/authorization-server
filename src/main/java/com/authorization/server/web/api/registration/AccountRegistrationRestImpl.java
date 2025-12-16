package com.authorization.server.web.api.registration;

import java.util.Locale;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.application.port.inbound.RegisterAccountUseCase;
import com.authorization.server.web.api.response.Message;
import com.authorization.server.web.api.response.Result;
import com.authorization.server.web.api.response.I18n;
import com.authorization.server.web.constant.Web;
import com.authorization.server.web.dto.RegisterRequestDto;
import com.authorization.server.web.dto.RegisterResponseDto;

@RestController
@RequestMapping(Web.Route.BASE_PATH)
public class AccountRegistrationRestImpl implements AccountRegistrationRestDefinition {

    private final I18n i18n;
    private final RegisterAccountUseCase registerAccountUseCase;
    private final ConversionService converter;

    public AccountRegistrationRestImpl(I18n i18n, RegisterAccountUseCase registerAccountUseCase, ConversionService converter) {
        this.i18n = i18n;
        this.registerAccountUseCase = registerAccountUseCase;
        this.converter = converter;
    }

    @Override
    @PostMapping(Web.Route.REGISTRATION_PATH)
    public ResponseEntity<Result<?>> registerAccount(@RequestBody @Validated RegisterRequestDto requestDto, BindingResult bindingResult, Locale locale) {
        if (bindingResult.hasErrors()) {
            String message = i18n.getMessage(Web.Validation.VALIDATION_FAILED, locale);

            var errors = bindingResult.getFieldErrors().stream()
                    .map(f -> new Message(Web.MessageSeverity.INFO, f.getDefaultMessage()))
                    .toList();

            var result = Result.onFailure()
                            .withCode(HttpStatus.BAD_REQUEST)
                            .withMessage(message)
                            .withErrors(errors)
                            .build();

            return ResponseEntity.status(result.code()).body(result);
        }

        RegisterCommand registerCommand = converter.convert(requestDto, RegisterCommand.class);
        var registerCommandResult = registerAccountUseCase.handle(registerCommand);
        var registerResponseDto = converter.convert(registerCommandResult, RegisterResponseDto.class);
        String message = i18n.getMessage(Web.AccountFlow.REGISTRATION_SUCCESS, locale);

        var result = Result.onSuccess()
                        .withCode(HttpStatus.CREATED)
                        .withData(registerResponseDto)
                        .withMessage(message)
                        .build();

        return ResponseEntity.status(result.code()).body(result);
    }
}