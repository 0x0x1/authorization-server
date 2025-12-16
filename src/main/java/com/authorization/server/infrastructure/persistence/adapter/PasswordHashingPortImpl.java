package com.authorization.server.infrastructure.persistence.adapter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.PasswordHashingPort;

@Component
public class PasswordHashingPortImpl implements PasswordHashingPort {

    private final PasswordEncoder passwordEncoder;

    public PasswordHashingPortImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }
}
