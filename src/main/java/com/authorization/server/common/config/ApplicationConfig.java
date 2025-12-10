package com.authorization.server.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.authorization.server.application.usecase.RegisterAccountUseCaseImpl;
import com.authorization.server.application.validation.RegisterAccountUseCaseValidator;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.converter.dto.AccountToRegisterResponseDtoConverter;
import com.authorization.server.infrastructure.persistence.jpa.converter.dto.RegisterRequestDtoToAccountConverter;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterAccountUseCaseImpl registerAccountUseCase(AccountRepository accountRepository,
                                                             RegisterRequestDtoToAccountConverter toAccount,
                                                             AccountToRegisterResponseDtoConverter toRegisterResponseDto, RegisterAccountUseCaseValidator accountUseCaseValidator) {
        return new RegisterAccountUseCaseImpl(accountRepository, toAccount, toRegisterResponseDto, accountUseCaseValidator);
    }
}