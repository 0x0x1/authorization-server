package com.authorization.server.domain.account;

import java.time.Instant;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private final UUID id;
    private final Username username;
    private final Password password;
    private final EmailAddress email;
    private final Set<RoleType> roleTypes;
    private final AccountState accountState;
    private static final Random random = new Random();

    private final Boolean isAccountEnabled;
    private final Boolean isAccountPasswordNotExpired;

    private Account(AccountBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.roleTypes = builder.roleTypes;
        this.accountState = builder.accountState;
        this.isAccountEnabled = builder.isAccountEnabled != null ? builder.isAccountEnabled : false;
        this.isAccountPasswordNotExpired = builder.isAccountPasswordExpired != null ? builder.isAccountPasswordExpired : false;
    }

    Boolean isAccountEnabled() {
        return getAccountState().getAccountStatus().isActive();
    }

    public Set<Permission> getAllPermissions() {
        return getRoleTypes().stream()
                .flatMap(roleType -> roleType.getPermissions().stream())
                .collect(Collectors.toSet());
    }

    Boolean hasPermission(String permissionName) {
        return getAllPermissions().stream()
                .anyMatch(permission -> permission.getPermissionName().equals(permissionName));
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
        private UUID id;
        private Username username;
        private Password password;
        private EmailAddress email;
        private Set<RoleType> roleTypes;
        private AccountState accountState;
        private Boolean isAccountEnabled;
        private Boolean isAccountPasswordExpired;

        public AccountBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AccountBuilder username(Username name) {
            this.username = name;
            return this;
        }

        public AccountBuilder password(Password password) {
            this.password = password;
            return this;
        }

        public AccountBuilder email(EmailAddress email) {
            this.email = email;
            return this;
        }

        public AccountBuilder roleTypes(Set<RoleType> roleType) {
            this.roleTypes = roleType;
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
        return Objects.equals(username, account.username) && Objects.equals(password, account.password) && Objects.equals(email, account.email) && roleTypes == account.roleTypes && Objects.equals(accountState, account.accountState) && Objects.equals(isAccountEnabled, account.isAccountEnabled) && Objects.equals(isAccountPasswordNotExpired, account.isAccountPasswordNotExpired);
    }

    private static UUID generateUUIDv7() {
        long epochMillis = Instant.now().toEpochMilli();
        long mostSigBits = (epochMillis & 0xFFFFFFFFFFFFL) << 16;
        mostSigBits |= 0x7000; // version 7
        mostSigBits |= random.nextInt(0x1000);
        long leastSigBits = random.nextLong();
        return new UUID(mostSigBits, leastSigBits);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(password);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(roleTypes);
        result = 31 * result + Objects.hashCode(accountState);
        result = 31 * result + Objects.hashCode(isAccountEnabled);
        result = 31 * result + Objects.hashCode(isAccountPasswordNotExpired);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roleTypes=" + roleTypes +
                ", accountState=" + accountState +
                ", isActive=" + isAccountEnabled +
                ", isPasswordExpired=" + isAccountPasswordNotExpired +
                '}';
    }
}