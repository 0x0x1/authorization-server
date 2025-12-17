package com.authorization.server.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.authorization.server.web.dto.RegisterRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration test for rate limiting on account registration endpoint.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "resilience4j.ratelimiter.instances.accountRegistration.limit-for-period=3",
        "resilience4j.ratelimiter.instances.accountRegistration.limit-refresh-period=10s"
})
public class AccountRegistrationRateLimiterTest {

    /** Endpoint path for registering a new user account. */
    public static final String REGISTRATION_PATH = "/rest/auth/public/registerAccount";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAllowRequestsWithinRateLimit() throws Exception {
        // When/Then - First 3 requests should succeed (or at least not be rate limited)
        for (int i = 0; i < 3; i++) {

            RegisterRequestDto request = createValidRegistrationRequest(i + "user1");

            mockMvc.perform(post(REGISTRATION_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().is(org.hamcrest.Matchers.is(201)));
        }
     }

    @Test
    void shouldRejectRequestsExceedingRateLimit() throws Exception {
        // When - Make requests up to the limit
        for (int i = 0; i < 3; i++) {
            RegisterRequestDto request = createValidRegistrationRequest(i + "user2");
            mockMvc.perform(post(REGISTRATION_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
        }

        RegisterRequestDto request = createValidRegistrationRequest("user2");

        // Then - The 4th request should be rate limited
        mockMvc.perform(post(REGISTRATION_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isTooManyRequests())
                .andExpect(jsonPath("$.code").value(429))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturnLocalizedRateLimitMessage() throws Exception {
        // When - Exceed rate limit
        for (int i = 0; i < 4; i++) {
            RegisterRequestDto request = createValidRegistrationRequest(i + "user3");
            mockMvc.perform(post(REGISTRATION_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
        }

        RegisterRequestDto request = createValidRegistrationRequest("user3");

        // Then - Check German message
        mockMvc.perform(post(REGISTRATION_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "de")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isTooManyRequests())
                .andExpect(jsonPath("$.message").value(
                        org.hamcrest.Matchers.containsString("Zu viele Anfragen. Bitte versuchen Sie es später erneut.")
                ));
    }

    private RegisterRequestDto createValidRegistrationRequest(String username) {
        return RegisterRequestDto.builder()
                .username(username)
                .password("password")
                .email(username + "test@email.com")
                .roles(List.of("manager"))
                .build();
    }
}