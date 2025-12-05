package com.authorization.server.infrastructure.fixture;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.catalina.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountLifecycleStatus;
import com.authorization.server.identity.AccountLockStatus;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.identity.Credentials;
import com.authorization.server.identity.EmailAddress;
import com.authorization.server.identity.Password;
import com.authorization.server.identity.Username;
import com.authorization.server.infrastructure.persistence.jpa.contract.PermissionRepository;
import com.authorization.server.infrastructure.persistence.jpa.contract.RoleRepository;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.AccountEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.CredentialsEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.EmailAddressEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.PasswordEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.UsernameEntity;

import ro.polak.springboot.datafixtures.DataFixture;
import ro.polak.springboot.datafixtures.DataFixtureSet;

@Component
@Order(3)
public class AccountSeed implements DataFixture {

    private final AccountRepository accountRepo;
    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;

    public AccountSeed(AccountRepository accountRepo, RoleRepository roleRepo, PermissionRepository permissionRepo) {
        this.accountRepo = accountRepo;
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public DataFixtureSet getSet() {
        return DataFixtureSet.DICTIONARY;
    }

    @Override
    public boolean canBeLoaded() {
        return accountRepo.count() == 0;
    }

    @Override
    public void load() {

        UUID adminRoleId = roleRepo.findByDisplayName("admin").getId();
        UUID managerRoleId = roleRepo.findByDisplayName("manager").getId();

        var account = Account.builder()
                .credentials(new Credentials(new Username("admin"), new Password("root")))
                .emailAddress(new EmailAddress("test@test.com"))
                .roleIds(List.of(adminRoleId, managerRoleId))
                .build();

        accountRepo.save(account);
    }
}