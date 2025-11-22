package com.authorization.server.domain.account;

import java.util.Objects;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private final String username;
    private final String password;
    private final String email;
    private final RoleType roleType;
    private final AccountState accountState;

    private final Boolean isAccountEnabled;
    private final Boolean isPasswordExpired;

    private Account(AccountBuilder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.roleType = builder.roleType;
        this.accountState = builder.accountState;
        this.isAccountEnabled = builder.isAccountEnabled;
        this.isPasswordExpired = builder.isAccountPasswordExpired;
    }

    Boolean isAccountEnabled() {
        return getAccountState().getAccountStatus().isActive();
    }

    public void enableAccount() {
        getAccountState().enable();
    }

    public void disableAccount() {
        getAccountState().disable();
    }

    public void resetAccountPassword() {
        throw new UnsupportedOperationException("Reset password is currently not supported for CustomerAccount");
    }

    public Account getAccount() {
        return null;
    }

    // Builder Design Pattern
    public static class AccountBuilder {
        private String username;
        private String password;
        private String email;
        private RoleType roleType;
        private AccountState accountState;
        private Boolean isAccountEnabled;
        private Boolean isAccountPasswordExpired;

        public AccountBuilder username(String name) {
            this.username = name;
            return this;
        }

        public AccountBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AccountBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AccountBuilder roleType(RoleType roleType) {
            this.roleType = roleType;
            return this;
        }

        public AccountBuilder accountState(AccountState state) {
            this.accountState = state;
            return this;
        }

        public AccountBuilder isAccountEnabled(Boolean isActive) {
            this.isAccountEnabled = isActive;
            return this;
        }

        public AccountBuilder isAccountPasswordExpired(Boolean isPasswordExpired) {
            this.isAccountPasswordExpired = isPasswordExpired;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Account account = (Account) object;
        return Objects.equals(username, account.username) && Objects.equals(password, account.password) && Objects.equals(email, account.email) && roleType == account.roleType && Objects.equals(accountState, account.accountState) && Objects.equals(isAccountEnabled, account.isAccountEnabled) && Objects.equals(isPasswordExpired, account.isPasswordExpired);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(password);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(roleType);
        result = 31 * result + Objects.hashCode(accountState);
        result = 31 * result + Objects.hashCode(isAccountEnabled);
        result = 31 * result + Objects.hashCode(isPasswordExpired);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + roleType +
                ", accountState=" + accountState +
                ", isActive=" + isAccountEnabled +
                ", isPasswordExpired=" + isPasswordExpired +
                '}';
    }
}