package com.authorization.server.infrastructure.persistence.jpa.adapter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.PasswordHashingService;

@Component
public class PasswordHashingServiceImpl implements PasswordHashingService {

    private final PasswordEncoder passwordEncoder;

    public PasswordHashingServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }
}
