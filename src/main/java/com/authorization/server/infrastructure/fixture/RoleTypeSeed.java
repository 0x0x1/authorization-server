package com.authorization.server.infrastructure.fixture;

import java.util.HashSet;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.authorization.server.infrastructure.persistence.jpa.contract.PermissionRepository;
import com.authorization.server.infrastructure.persistence.jpa.contract.RoleTypeRepository;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.RoleTypeEntity;

import ro.polak.springboot.datafixtures.DataFixture;
import ro.polak.springboot.datafixtures.DataFixtureSet;

@Component
@Order(2)
public class RoleTypeSeed implements DataFixture {

    private final RoleTypeRepository roleTypeRepo;
    private final PermissionRepository permissionRepo;

    public RoleTypeSeed(RoleTypeRepository roleTypeRepo, PermissionRepository permissionRepo) {
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
        System.out.println(permissionRepo.findAll());
        var admin = new RoleTypeEntity();
        admin.setRoleTypeName("ADMIN");
        admin.setPermissionEntities(new HashSet<>(permissionRepo.findAll()));

        var manager = new RoleTypeEntity();
        manager.setRoleTypeName("MANAGER");
        manager.setPermissionEntities(new HashSet<>(permissionRepo.findAll().stream().filter(p -> !p.getPermissionName().equals("DELETE")).toList()));

        Set<RoleTypeEntity> seed = Set.of(admin, manager);
        roleTypeRepo.saveAll(seed);
    }
}