package com.authorization.server.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.authorization.server.application.usecase.RegisterAccountUseCaseImpl;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.converter.AccountFactory;
import com.authorization.server.infrastructure.persistence.repository.RoleRepository;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterAccountUseCaseImpl registerAccountUseCase(AccountRepository accountRepository, RoleRepository roleRepository, AccountFactory accountFactory) {
        return new RegisterAccountUseCaseImpl(accountRepository, roleRepository, accountFactory);
    }
}