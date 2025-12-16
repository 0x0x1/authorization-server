package com.authorization.server.identity;

import static com.authorization.server.identity.AccountUtil.generateUUIDv7;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "credentials")
/*
 * The Authoritative Principal representing the user's identity in the Authorization system.
 */
public class Account {

    /*
     * Default configuration during account creation.
     */
    private final UUID id;
    private AccountLifecycleStatus accountLifecycleStatus;
    private AccountLockStatus accountLockStatus;
    private Instant createdAt;

    /*
     * Inputs during account creation.
     */
    private final Credentials credentials;
    private final EmailAddress emailAddress;
    private final Collection<UUID> roleIds;

    private Account(AccountBuilder builder) {
        if (builder.credentials == null || builder.emailAddress == null) {
            throw new IllegalStateException("Credentials and email address must be provided");
        }

        /*
         * Use defaults or explicit values based on builder configuration.
         */
        this.id = builder.id != null ? builder.id : generateUUIDv7();
        this.accountLifecycleStatus = builder.accountLifecycleStatus != null
                ? builder.accountLifecycleStatus
                : AccountLifecycleStatus.ENABLED;
        this.accountLockStatus = builder.accountLockStatus != null
                ? builder.accountLockStatus
                : AccountLockStatus.UNLOCKED;
        this.createdAt = builder.createdAt != null
                ? builder.createdAt
                : Instant.now();

        /*
         * Explicitly defined by the user or system admin.
         */
        this.credentials = builder.credentials;
        this.emailAddress = builder.emailAddress;

        /*
         * Defensive copy to protect the Set from the caller.
         */
        this.roleIds = builder.roleIds != null ? new HashSet<>(builder.roleIds) : new HashSet<>();
    }

    /*
     * Checks whether the current Account is enabled.
     */
    Boolean isAccountEnabled() {
        return this.accountLifecycleStatus == AccountLifecycleStatus.ENABLED;
    }

    /*
     * Checks whether the current Account is unlocked.
     */
    Boolean isAccountUnLocked() {
        return this.accountLockStatus == AccountLockStatus.UNLOCKED;
    }

    /*
     * Enables Account.
     */
    public void enableAccount() {
        if (this.accountLifecycleStatus == AccountLifecycleStatus.ENABLED) {
            throw new IllegalStateException("Account is already enabled");
        }
        this.accountLifecycleStatus = AccountLifecycleStatus.ENABLED;
    }

    /*
     * Disables Account.
     */
    public void disableAccount() {
        if (this.accountLifecycleStatus == AccountLifecycleStatus.DISABLED) {
            throw new IllegalStateException("Account is already disabled");
        }
        this.accountLifecycleStatus = AccountLifecycleStatus.DISABLED;
    }

    /*
     * Locks Account.
     */
    public void lockAccount() {
        if (accountLockStatus == AccountLockStatus.LOCKED) {
            throw new IllegalStateException("Account is already locked");
        }
        accountLockStatus = AccountLockStatus.LOCKED;
        this.createdAt = Instant.now();
    }

    /*
     * Unlocks Account.
     */
    public void unlockAccount() {
        if (accountLockStatus == AccountLockStatus.UNLOCKED) {
            throw new IllegalStateException("Account is already unlocked");
        }
        accountLockStatus = AccountLockStatus.UNLOCKED;
        this.createdAt = Instant.now();
    }

    /*
     * Returns immutable rolesId set.
     */
    public Collection<UUID> getRoleIds() {
        return Collections.unmodifiableCollection(roleIds);
    }

    /*
     * Assigns a new role to Account. Business rule compliant.
     */
    public void assignNewRole(UUID roleId) {
        if (roleId == null) {
            throw new IllegalStateException("Role ID cannot be null");
        }

        if (this.accountLifecycleStatus == AccountLifecycleStatus.DISABLED || this.accountLockStatus == AccountLockStatus.LOCKED) {
            throw new IllegalStateException("Cannot assign role to a disabled or locked account");
        }

        if (roleIds.contains(roleId)) {
            return;
        }

        roleIds.add(roleId);
    }

    /*
     * Removes an existing role from Account. Business rule compliant.
     */
    public void removeRole(UUID roleId) {
        if (roleId == null) {
            throw new IllegalStateException("Role ID cannot be null");
        }

        if (this.accountLifecycleStatus == AccountLifecycleStatus.DISABLED || this.accountLockStatus == AccountLockStatus.LOCKED) {
            throw new IllegalStateException("Cannot remove role from a disabled or locked account");
        }

        if (!roleIds.contains(roleId)) {
            return;
        }

        roleIds.remove(roleId);
    }

    /*
     * Decides whether the user associated with this Account can login to the system.
     */
    public boolean canLogin() {
        return accountLifecycleStatus == AccountLifecycleStatus.ENABLED
                && accountLockStatus == AccountLockStatus.UNLOCKED;
    }

    /*
     * Builder Pattern to create an object of type Account.
     */
    public static class AccountBuilder {
        // Required fields
        private Credentials credentials;
        private EmailAddress emailAddress;

        // Optional fields with defaults
        private UUID id;
        private AccountLifecycleStatus accountLifecycleStatus;
        private AccountLockStatus accountLockStatus;
        private Instant createdAt;
        private Collection<UUID> roleIds;

        public AccountBuilder credentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public AccountBuilder emailAddress(EmailAddress emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public AccountBuilder roleIds(Collection<UUID> roleIds) {
            this.roleIds = roleIds;
            return this;
        }

        /*
         * Explicit configuration methods for entity-to-domain mapping.
         */
        public AccountBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AccountBuilder accountLifecycleStatus(AccountLifecycleStatus accountLifecycleStatus) {
            this.accountLifecycleStatus = accountLifecycleStatus;
            return this;
        }

        public AccountBuilder accountLockStatus(AccountLockStatus accountLockStatus) {
            this.accountLockStatus = accountLockStatus;
            return this;
        }

        public AccountBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

    /*
     * Convenient method for caller to get the instance of the builder - e.g Account.builder()
     */
    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    /*
     * Checks whether the password is correct when user attempts to log in.
     */
    public Boolean isPasswordCorrect(Password candidate) {
        return credentials.password().matches(candidate);
    }

    /*
     * Checks whether the username is correct when user attempts to log in.
     */
    public Boolean isUsernameCorrect(Username candidate) {
        return credentials.username().matches(candidate);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Account account = (Account) object;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}