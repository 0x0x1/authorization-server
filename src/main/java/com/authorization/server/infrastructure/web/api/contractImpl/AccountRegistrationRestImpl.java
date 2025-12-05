package com.authorization.server.infrastructure.web.api.contractImpl;

import static com.authorization.server.core.ApplicationConstants.CREATED;
import static com.authorization.server.core.ApplicationConstants.SIGN_UP_SUCCESS;

import java.util.Locale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.server.infrastructure.web.api.contract.AccountRegistrationRestDefinition;
import com.authorization.server.infrastructure.web.api.delegator.ApiProcessor;
import com.authorization.server.core.Result;
import com.authorization.server.core.I18n;
import com.authorization.server.infrastructure.web.dto.RegisterRequestDto;

@RestController
@RequestMapping(AccountRegistrationRestDefinition.BASE_PATH)
public class AccountRegistrationRestImpl implements AccountRegistrationRestDefinition {

    private final I18n i18n;
    private final ApiProcessor apiProcessor;

    public AccountRegistrationRestImpl(I18n i18n, ApiProcessor apiProcessor) {
        this.i18n = i18n;
        this.apiProcessor = apiProcessor;

    }

    @Override
    @PostMapping(REGISTRATION_PATH)
    public ResponseEntity<Result<?>> registerAccount(@RequestBody RegisterRequestDto requestDto, Locale locale) {
        //TODO: step validate dto
        var registerResponseDto = apiProcessor.registerAccount(requestDto);// user registered in the system but disabled
        var location = apiProcessor.getResourceLocation(registerResponseDto);
        var localMessage = i18n.getMessage(SIGN_UP_SUCCESS, locale);

        var result = Result.status(CREATED).data(registerResponseDto).message(localMessage).build();
        return ResponseEntity.created(location).body(result);
    }
}