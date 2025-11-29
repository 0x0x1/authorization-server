package com.authorization.server.infrastructure.fixture;

import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.authorization.server.infrastructure.persistence.jpa.contract.PermissionRepository;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.PermissionEntity;

import ro.polak.springboot.datafixtures.DataFixture;
import ro.polak.springboot.datafixtures.DataFixtureSet;

/**
 * Domain constants. System level configuration.
 */
@Component
@Order(1)
public class PermissionSeed implements DataFixture {

    private final PermissionRepository permissionRepo;

    public PermissionSeed(PermissionRepository permissionRepo) {
        this.permissionRepo = permissionRepo;
    }

    @Override
    public DataFixtureSet getSet() {
        return DataFixtureSet.DICTIONARY;
    }

    @Override
    public boolean canBeLoaded() {
        return permissionRepo.count() == 0;
    }

    @Override
    public void load() {
        Set<PermissionEntity> seed = Set.of(
                new PermissionEntity(null, "READ", "permission to read data"),
                new PermissionEntity(null, "WRITE", "permission to modify data"),
                new PermissionEntity(null, "DELETE", "permission to delete data"));
        permissionRepo.saveAll(seed);
    }
}