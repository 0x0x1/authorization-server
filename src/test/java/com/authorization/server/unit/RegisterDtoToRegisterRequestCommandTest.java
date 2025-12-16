package com.authorization.server.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.infrastructure.persistence.converter.RegisterRequestDtoToRegisterCommand;
import com.authorization.server.web.dto.RegisterRequestDto;

@DisplayName("RegisterRequestDto to RegisterCommand Converter Tests")
public class RegisterDtoToRegisterRequestCommandTest extends BaseTest {

    private Converter<RegisterRequestDto, RegisterCommand> converter;

    @BeforeEach
    void setUp() {
        converter = new RegisterRequestDtoToRegisterCommand();
    }

    @Nested
    @DisplayName("Successful conversion scenarios")
    class SuccessfulConversion {

        @Test
        @DisplayName("Should convert DTO with single role to Command")
        void shouldConvertDtoWithSingleRole() {
            var dto = new RegisterRequestDto(USERNAME, PASSWORD, EMAIL, List.of(ROLE_ADMIN));

            var command = converter.convert(dto);

            assertThat(command).isNotNull();
            assertThat(command.username()).isEqualTo(USERNAME);
            assertThat(command.password()).isEqualTo(PASSWORD);
            assertThat(command.email()).isEqualTo(EMAIL);
            assertThat(command.roles()).containsExactly(ROLE_ADMIN);
        }

        @Test
        @DisplayName("Should convert DTO with multiple roles to Command")
        void shouldConvertDtoWithMultipleRoles() {
            var dto = new RegisterRequestDto(USERNAME, PASSWORD, EMAIL, ROLES);

            var command = converter.convert(dto);

            assertThat(command).isNotNull();
            assertThat(command.roles()).containsExactlyElementsOf(ROLES);
        }

        @Test
        @DisplayName("Should convert DTO with empty roles list")
        void shouldConvertDtoWithEmptyRoles() {
            var dto = new RegisterRequestDto(USERNAME, PASSWORD, EMAIL, Collections.emptyList());

            var command = converter.convert(dto);

            assertThat(command).isNotNull();
            assertThat(command.roles()).isEmpty();
        }

        @Test
        @DisplayName("Should preserve special characters in fields")
        void shouldPreserveSpecialCharacters() {
            var specialUsername = "user@123";
            var specialPassword = "P@ssw0rd!#$";
            var dto = new RegisterRequestDto(specialUsername, specialPassword, EMAIL, List.of(ROLE_ADMIN));

            var command = converter.convert(dto);

            assertThat(command.username()).isEqualTo(specialUsername);
            assertThat(command.password()).isEqualTo(specialPassword);
        }
    }

    @Nested
    @DisplayName("Edge cases and null handling")
    class EdgeCasesAndNullHandling {

        @Test
        @DisplayName("Should handle null source DTO gracefully")
        void convert_shouldThrowException_whenDtoIsNull() {
            assertThatThrownBy(() -> converter.convert(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("RegisterRequestDto must not be null");
        }

        @Test
        @DisplayName("Should handle null source DTO gracefully")
        void convert_shouldThrowException_whenDtoRolesIsNull() {
            var dto = new RegisterRequestDto(USERNAME, PASSWORD, EMAIL, null);
            assertThatThrownBy(() -> converter.convert(dto))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("Roles must not be null");
        }

        @Test
        @DisplayName("Should handle empty string values")
        void shouldHandleEmptyStrings() {
            var dto = new RegisterRequestDto("", "", "", List.of(ROLE_ADMIN));

            var command = converter.convert(dto);

            assertThat(command).isNotNull();
            assertThat(command.username()).isEmpty();
            assertThat(command.password()).isEmpty();
            assertThat(command.email()).isEmpty();
        }
    }
}