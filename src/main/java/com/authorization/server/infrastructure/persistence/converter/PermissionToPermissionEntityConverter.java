package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.stereotype.Component;

import com.authorization.server.domain.account.Permission;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.PermissionEntity;

@Component
public class PermissionToPermissionEntityConverter implements Converter<Permission, PermissionEntity> {
    @Override
    public PermissionEntity convert(Permission fromSource) {
        if (fromSource == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }

        var permissionEntity = new PermissionEntity();
        permissionEntity.setPermissionName(fromSource.getPermissionName());
        permissionEntity.setDescription(fromSource.getDescription());
        return permissionEntity;
    }

    @Override
    public Permission reverse(PermissionEntity fromTarget) {
        throw new UnsupportedOperationException();
    }
}
