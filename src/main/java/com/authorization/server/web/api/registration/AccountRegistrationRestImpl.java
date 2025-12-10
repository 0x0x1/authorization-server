package com.authorization.server.web.api.registration;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.server.application.port.inbound.RegisterAccountUseCase;
import com.authorization.server.web.api.response.Result;
import com.authorization.server.web.api.response.I18n;
import com.authorization.server.web.constant.Web;
import com.authorization.server.web.dto.RegisterRequestDto;

@RestController
@RequestMapping(Web.Route.BASE_PATH)
public class AccountRegistrationRestImpl implements AccountRegistrationRestDefinition {

    private final I18n i18n;
    private final RegisterAccountUseCase registerAccountUseCase;

    public AccountRegistrationRestImpl(I18n i18n, RegisterAccountUseCase registerAccountUseCase) {
        this.i18n = i18n;
        this.registerAccountUseCase = registerAccountUseCase;
    }

    @Override
    @PostMapping(Web.Route.REGISTRATION_PATH)
    public ResponseEntity<Result<?>> registerAccount(@RequestBody RegisterRequestDto requestDto, Locale locale) {
        var registerResponseDto = registerAccountUseCase.register(requestDto);
        String message = i18n.getMessage(Web.AccountFlow.SIGN_UP_SUCCESS, locale);

        var result = Result.onSuccess()
                                .withCode(HttpStatus.CREATED)
                                .withData(registerResponseDto)
                                .withMessage(message)
                                .build();

        return ResponseEntity.status(result.code()).body(result);
    }
}