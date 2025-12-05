package com.authorization.server.infrastructure.fixture;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.authorization.server.identity.AccountLifecycleStatus;
import com.authorization.server.identity.AccountLockStatus;
import com.authorization.server.identity.AccountRepository;
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

        var username = new UsernameEntity("admin");
        var password = new PasswordEntity("password");
        var credentials = new CredentialsEntity(username, password);

        var accountEntity = new AccountEntity();
        accountEntity.setCredentials(credentials);
        accountEntity.setRoleEntities(roleRepo.findAll());
        accountEntity.setEmail(new EmailAddressEntity("test@test.com"));
        accountEntity.setLockStatus(AccountLockStatus.UNLOCKED);
        accountEntity.setLifecycleStatus(AccountLifecycleStatus.ENABLED);
        accountEntity.setLastStatusChangeAt(Instant.now());

        Collection<AccountEntity> accountEntities = Set.of(accountEntity);
        accountRepo.save(accountEntity);
    }
}
