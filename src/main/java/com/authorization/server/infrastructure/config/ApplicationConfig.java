package com.authorization.server.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.authorization.server.application.port.outbound.PasswordHashingService;
import com.authorization.server.application.usecase.RegisterAccountUseCase;
import com.authorization.server.core.validation.RegisterAccountUseCaseValidator;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.identity.Role;
import com.authorization.server.infrastructure.persistence.converter.Converter;
import com.authorization.server.infrastructure.persistence.jpa.contract.RoleTypeRepository;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleTypeEntity;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterAccountUseCase registerAccountUseCase(AccountRepository accountRepository,
                                                         RegisterAccountUseCaseValidator accountValidator,
                                                         PasswordHashingService passwordEncoder, RoleTypeRepository roleTypeRepository, Converter<Role, RoleTypeEntity> roleTypeMapper) {
        return new RegisterAccountUseCase(accountRepository, accountValidator, passwordEncoder, roleTypeRepository, roleTypeMapper);
    }

}
