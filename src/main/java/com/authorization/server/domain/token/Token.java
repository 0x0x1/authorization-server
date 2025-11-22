package com.authorization.server.domain.token;

public sealed interface Token permits AccountToken {

    TokenType getToken();
}
