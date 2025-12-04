package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.stereotype.Component;

import com.authorization.server.identity.Permission;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.PermissionEntity;

@Component
public class PermissionToPermissionEntityConverter implements Converter<Permission, PermissionEntity> {
    @Override
    public PermissionEntity toEntity(Permission fromSource) {
        if (fromSource == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }

        var permissionEntity = new PermissionEntity();
        permissionEntity.setPermissionName(fromSource.displayName());
        permissionEntity.setDescription(fromSource.description());
        return permissionEntity;
    }

    @Override
    public Permission toDomain(PermissionEntity fromTarget) {
        if (fromTarget == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }

        return new Permission(fromTarget.getPermissionName(), fromTarget.getDescription());
    }
}
