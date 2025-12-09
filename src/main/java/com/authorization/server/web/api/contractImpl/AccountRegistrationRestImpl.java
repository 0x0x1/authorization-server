package com.authorization.server.web.api.contractImpl;

import static com.authorization.server.core.ApplicationConstants.CREATED;
import static com.authorization.server.core.ApplicationConstants.SIGN_UP_SUCCESS;
import static com.authorization.server.core.constant.ApiConstants.BASE_PATH;
import static com.authorization.server.core.constant.ApiConstants.REGISTRATION_PATH;

import java.util.Locale;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.server.application.port.inbound.RegisterAccountUseCase;
import com.authorization.server.web.api.contract.AccountRegistrationRestDefinition;
import com.authorization.server.core.Result;
import com.authorization.server.core.I18n;
import com.authorization.server.web.dto.RegisterRequestDto;

@RestController
@RequestMapping(BASE_PATH)
public class AccountRegistrationRestImpl implements AccountRegistrationRestDefinition {

    private final I18n i18n;
    private final RegisterAccountUseCase registerAccountUseCase;

    public AccountRegistrationRestImpl(I18n i18n, RegisterAccountUseCase registerAccountUseCase) {
        this.i18n = i18n;
        this.registerAccountUseCase = registerAccountUseCase;
    }

    @Override
    @PostMapping(REGISTRATION_PATH)
    public ResponseEntity<Result<?>> registerAccount(@RequestBody RegisterRequestDto requestDto, Locale locale) {
        //TODO: step validate dto
        Objects.requireNonNull(requestDto);
        var registerResponseDto = registerAccountUseCase.register(requestDto);// user registered in the system but disabled
        var localMessage = i18n.getMessage(SIGN_UP_SUCCESS, locale);

        var result = Result.status(CREATED).data(registerResponseDto).message(localMessage).build();
        return ResponseEntity.status(CREATED).body(result);
    }
}