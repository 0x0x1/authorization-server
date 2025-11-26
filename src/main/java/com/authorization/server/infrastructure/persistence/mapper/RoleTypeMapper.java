package com.authorization.server.infrastructure.persistence.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.authorization.server.domain.account.RoleType;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.PermissionEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.RoleTypeEntity;

@Component
public class RoleTypeMapper implements Mapper<RoleType, RoleTypeEntity> {

    private final PermissionMapper permissionMapper;

    public RoleTypeMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public RoleTypeEntity convert(RoleType dataContainer) {
        if (dataContainer == null) {
            throw new IllegalArgumentException("RoleType cannot be null");
        }

        Set<PermissionEntity> permissionEntities = dataContainer.getPermissions().stream()
                .map(permissionMapper::convert).collect(Collectors.toSet());

        var roleTypeEntity = new RoleTypeEntity();
        roleTypeEntity.setPermissionEntities(permissionEntities);
        roleTypeEntity.setDisplayName(dataContainer.getDisplayName());

        return roleTypeEntity;
    }
}