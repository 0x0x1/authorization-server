package com.authorization.server.authorization;

public sealed interface Token permits AccountToken {

    TokenType getToken();
}
