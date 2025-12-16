package com.authorization.server.domain;

public record EmailAddress (String emailAddress) {

    @Override
    public String toString() {
        return emailAddress;
    }
}