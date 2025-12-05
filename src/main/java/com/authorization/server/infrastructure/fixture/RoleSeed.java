package com.authorization.server.infrastructure.fixture;

import java.util.HashSet;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.authorization.server.infrastructure.persistence.jpa.contract.PermissionRepository;
import com.authorization.server.infrastructure.persistence.jpa.contract.RoleRepository;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleEntity;

import ro.polak.springboot.datafixtures.DataFixture;
import ro.polak.springboot.datafixtures.DataFixtureSet;

/**
 * Domain constants. System level configuration.
 */
@Component
@Order(2)
public class RoleSeed implements DataFixture {

    private final RoleRepository roleTypeRepo;
    private final PermissionRepository permissionRepo;

    public RoleSeed(RoleRepository roleTypeRepo, PermissionRepository permissionRepo) {
        this.roleTypeRepo = roleTypeRepo;
        this.permissionRepo = permissionRepo;
    }


    @Override
    public DataFixtureSet getSet() {
        return DataFixtureSet.DICTIONARY;
    }

    @Override
    public boolean canBeLoaded() {
        return roleTypeRepo.count() == 0;
    }

    @Override
    public void load() {
        var admin = new RoleEntity();
        admin.setDisplayName("ADMIN");
        admin.setPermissionEntities(new HashSet<>(permissionRepo.findAll()));

        var manager = new RoleEntity();
        manager.setDisplayName("MANAGER");
        manager.setPermissionEntities(new HashSet<>(permissionRepo.findAll().stream().filter(p -> !p.getDisplayName().equals("DELETE")).toList()));

        Set<RoleEntity> seed = Set.of(admin, manager);
        roleTypeRepo.saveAll(seed);
    }
}