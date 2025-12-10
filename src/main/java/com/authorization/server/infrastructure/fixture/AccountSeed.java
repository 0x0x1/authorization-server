package com.authorization.server.infrastructure.fixture;

import java.util.List;
import java.util.UUID;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.identity.Credentials;
import com.authorization.server.identity.EmailAddress;
import com.authorization.server.identity.Password;
import com.authorization.server.identity.Username;
import com.authorization.server.infrastructure.persistence.jpa.repository.PermissionRepository;
import com.authorization.server.infrastructure.persistence.jpa.repository.RoleRepository;

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