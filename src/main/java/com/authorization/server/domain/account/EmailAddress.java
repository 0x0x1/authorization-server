package com.authorization.server.domain.account;

import lombok.Getter;

@Getter
public class EmailAddress {
    private String emailAddress;

    public EmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
