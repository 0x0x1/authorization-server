package com.authorization.server.identity;

public record EmailAddress (String emailAddress) {

    @Override
    public String toString() {
        return emailAddress;
    }
}