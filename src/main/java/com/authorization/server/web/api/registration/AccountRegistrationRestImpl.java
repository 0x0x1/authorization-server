package com.authorization.server.web.api.registration;

import java.util.Locale;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.application.command.RegisterCommandResult;
import com.authorization.server.application.port.inbound.RegisterAccountUseCase;
import com.authorization.server.application.port.outbound.Converter;
import com.authorization.server.web.api.response.Message;
import com.authorization.server.web.api.response.Result;
import com.authorization.server.web.api.response.I18n;
import com.authorization.server.web.constant.Web;
import com.authorization.server.web.dto.RegisterRequestDto;

@RestController
@RequestMapping(Web.Route.BASE_PATH)
public class AccountRegistrationRestImpl implements AccountRegistrationRestDefinition {

    private final I18n i18n;
    private final RegisterAccountUseCase registerAccountUseCase;
    private final Converter<RegisterRequestDto, RegisterCommand> converter =
            dto -> new RegisterCommand(dto.username(), dto.email(), dto.password(), dto.roles());

    public AccountRegistrationRestImpl(I18n i18n, RegisterAccountUseCase registerAccountUseCase) {
        this.i18n = i18n;
        this.registerAccountUseCase = registerAccountUseCase;
    }

    @Override
    @PostMapping(Web.Route.REGISTRATION_PATH)
    public ResponseEntity<Result<?>> registerAccount(@RequestBody @Validated RegisterRequestDto requestDto, BindingResult bindingResult, Locale locale) {

        System.out.println(requestDto.email());
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

        Objects.requireNonNull(requestDto);
        RegisterCommand registerCommand = converter.convert(requestDto);

        // web/ui-application boundary
        RegisterCommandResult registerResponseDto = registerAccountUseCase.handle(registerCommand);

        String message = i18n.getMessage(Web.AccountFlow.REGISTRATION_SUCCESS, locale);

        var result = Result.onSuccess()
                        .withCode(HttpStatus.CREATED)
                        .withData(registerResponseDto)
                        .withMessage(message)
                        .build();

        return ResponseEntity.status(result.code()).body(result);
    }
}