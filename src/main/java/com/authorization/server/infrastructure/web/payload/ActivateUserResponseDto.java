package com.authorization.server.infrastructure.web.payload;

public class ActivateUserResponseDto {

    private boolean userEnabled;
    private String username;

    public ActivateUserResponseDto(boolean userEnabled, String username) {
        this.userEnabled = userEnabled;
        this.username = username;
    }

    public ActivateUserResponseDto() {
    }

    public boolean isUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(boolean userEnabled) {
        this.userEnabled = userEnabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
