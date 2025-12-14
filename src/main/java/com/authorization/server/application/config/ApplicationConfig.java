package com.authorization.server.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.authorization.server.application.usecase.RegisterAccountUseCaseImpl;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.converter.cmd.AccountToRegisterCommandResult;
import com.authorization.server.infrastructure.persistence.jpa.converter.cmd.RegisterCommandToAccount;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterAccountUseCaseImpl registerAccountUseCase(AccountRepository accountRepository, RegisterCommandToAccount cmdConverter,
                                                             AccountToRegisterCommandResult toRegisterCommandResult) {
        return new RegisterAccountUseCaseImpl(accountRepository, cmdConverter, toRegisterCommandResult);
    }
}