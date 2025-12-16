package com.authorization.server.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class WebConfig {

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
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
                )
                .build();
    }
}