package com.authorization.server.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.authorization.server.application.port.outbound.RoleReaderPort;
import com.authorization.server.application.usecase.RegisterAccountUseCaseImpl;
import com.authorization.server.identity.AccountFactoryPort;
import com.authorization.server.identity.AccountPersistencePort;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterAccountUseCaseImpl registerAccountUseCase(AccountPersistencePort accountPersistencePort, RoleReaderPort roleReaderPort, AccountFactoryPort accountFactoryPort) {
        return new RegisterAccountUseCaseImpl(accountPersistencePort, roleReaderPort, accountFactoryPort);
    }
}