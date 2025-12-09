package com.authorization.server.core.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.authorization.server.application.usecase.RegisterAccountUseCaseImpl;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.converter.dto.AccountToRegisterResponseDtoDtoConverter;
import com.authorization.server.infrastructure.persistence.converter.dto.RegisterRequestDtoToAccountDtoConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ApplicationConfig {

    /**
     * Configures the {@link MessageSource} to load localized message bundles.
     *
     * The message source supports reloading of message files and includes multiple base names
     * for organizing error and general messages separately.
     *
     * @return a configured {@code MessageSource} instance
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:i18n/error", "classpath:i18n/message");
        messageSource.setCacheSeconds(1);
        return messageSource;
    }

    /**
     * Configures a customized {@link ObjectMapper} for JSON processing.
     *
     * @return a customized {@code ObjectMapper} instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .indentOutput(true)
                .serializationInclusion(JsonInclude.Include.NON_EMPTY)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
    }

    @Bean
    public RegisterAccountUseCaseImpl registerAccountUseCase(AccountRepository accountRepository,
                                                             RegisterRequestDtoToAccountDtoConverter toAccount,
                                                             AccountToRegisterResponseDtoDtoConverter toRegisterResponseDto) {
        return new RegisterAccountUseCaseImpl(accountRepository, toAccount, toRegisterResponseDto);
    }
}