package com.authorization.server.unit;

import java.util.List;

public class BaseTest {

    protected static final String USERNAME = "admin";
    protected static final String PASSWORD = "root";
    protected static final String EMAIL = "admin@hotmail.com";
    protected static final String ROLE_ADMIN = "admin";
    protected static final String ROLE_MANAGER = "manager";
    protected static final List<String> ROLES = List.of("admin", "manager", "user");
}
