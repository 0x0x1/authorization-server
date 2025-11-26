package com.authorization.server.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.authorization.server.domain.account.Permission;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.PermissionEntity;

@Component
public class PermissionMapper implements Mapper<Permission, PermissionEntity> {
    @Override
    public PermissionEntity convert(Permission dataContainer) {
        if (dataContainer == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }

        var permissionEntity = new PermissionEntity();
        permissionEntity.setName(dataContainer.getName());
        permissionEntity.setDescription(dataContainer.getDescription());
        return permissionEntity;
    }
}
