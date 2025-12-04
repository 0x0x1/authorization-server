package com.authorization.server.authorization.token;

public sealed interface Token permits AccountToken {

    TokenType getToken();
}
