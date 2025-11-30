package com.authorization.server.domain.account;

import lombok.Getter;

@Getter
public class Username {
    private String username;

    public Username(String username) {
        this.username = username;
    }
}
