package com.authorization.server.domain.account;

import lombok.Getter;

@Getter
public class Password {
    private String password;

    public Password(String password) {
        this.password = password;
    }
}
