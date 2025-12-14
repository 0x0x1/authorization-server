package com.authorization.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.infrastructure.persistence.jpa.converter.RegisterRequestDtoToRegisterCommand;
import com.authorization.server.web.dto.RegisterRequestDto;

public class RegisterRequestDtoToRegisterCommandTest {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "root";
    private static final String EMAIL = "admin@hotmail.com";
    private static final String ROLE = "manager";

    private DefaultConversionService defaultConversionService;

    @BeforeEach
    void setUp() {
        defaultConversionService = new DefaultConversionService();
        defaultConversionService.addConverter(new RegisterRequestDtoToRegisterCommand());
    }

    @Test
    public void conversion_from_registerRequestDto_to_registerCommand_should_succeed() {
        var registerRequestDto = new RegisterRequestDto(USERNAME, PASSWORD, EMAIL, List.of(ROLE));
        var command = defaultConversionService.convert(registerRequestDto, RegisterCommand.class);

        assertThat(command).isNotNull();
        assertThat(command.username()).contains(USERNAME);
        assertThat(command.password()).contains(PASSWORD);
        assertThat(command.email()).contains(EMAIL);
        assertThat(command.roles()).contains(ROLE);
    }
}