package com.authorization.server.infrastructure.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConversionServiceConfig {

    @Bean
    @Primary
    public ConversionService conversionService(List<Converter<?, ?>> converters) {
        DefaultConversionService service = new DefaultConversionService();
        converters.forEach(service::addConverter);
        return service;
    }
}
