package com.authorization.server.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.authorization.server.application.service.AccountRegistrationService;
import com.authorization.server.domain.account.AccountRepository;

@Configuration
public class ApplicationConfig {

    @Bean
    public AccountRegistrationService accountRegistrationUseCase(AccountRepository acctRepository,
                                                                 PasswordEncoder passwordEncoder) {
        return new AccountRegistrationService(acctRepository, passwordEncoder);
    }
}
