package com.authorization.server.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.authorization.server.application.port.outbound.PasswordHashingService;
import com.authorization.server.application.usecase.RegisterAccountUseCase;
import com.authorization.server.core.validation.RegisterAccountUseCaseValidator;
import com.authorization.server.domain.account.AccountRepository;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterAccountUseCase registerAccountUseCase(AccountRepository accountRepository,
                                                         RegisterAccountUseCaseValidator accountValidator,
                                                         PasswordHashingService passwordEncoder) {
        return new RegisterAccountUseCase(accountRepository, accountValidator, passwordEncoder);
    }
}
