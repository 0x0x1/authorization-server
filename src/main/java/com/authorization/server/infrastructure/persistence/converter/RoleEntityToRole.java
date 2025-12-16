package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.identity.Permission;
import com.authorization.server.identity.Role;
import com.authorization.server.infrastructure.persistence.entity.authorization.PermissionEntity;
import com.authorization.server.infrastructure.persistence.entity.authorization.RoleEntity;

@Component
public class RoleEntityToRole implements Converter<RoleEntity, Role> {

    private final Converter<PermissionEntity, Permission> permissionConverter;

    public RoleEntityToRole(Converter<PermissionEntity, Permission> permissionConverter) {
        this.permissionConverter = permissionConverter;
    }

    @Override
    public Role convert(RoleEntity source) {
        if (source == null) {
            return null;
        }

        return new Role(source.getId(),
                source.getDisplayName(),
                source.getDescription(),
                source.getPermissionEntities().stream()
                        .map(permissionConverter::convert).toList());
    }
}
