package com.authorization.server.infrastructure.persistence.converter;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.domain.Permission;
import com.authorization.server.domain.Role;
import com.authorization.server.infrastructure.persistence.entity.authorization.PermissionEntity;
import com.authorization.server.infrastructure.persistence.entity.authorization.RoleEntity;

@Component
public class RoleToRoleEntity implements Converter<Role, RoleEntity> {

    private final Converter<Permission, PermissionEntity> permissionConverter;

    public RoleToRoleEntity(Converter<Permission, PermissionEntity> permissionConverter) {
        this.permissionConverter = permissionConverter;
    }

    @Override
    public RoleEntity convert(Role source) {
        if (source == null) {
            return null;
        }

        List<PermissionEntity> permissionEntities = source.permissions().stream()
                .map(permissionConverter::convert)
                .toList();

        var entity = new RoleEntity();
        entity.setDisplayName(source.displayName());
        entity.setDescription(source.description());
        entity.setPermissionEntities(permissionEntities);

        return entity;
    }
}