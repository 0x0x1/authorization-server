package com.authorization.server.infrastructure.persistence.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.authorization.server.identity.Permission;
import com.authorization.server.identity.Role;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.PermissionEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleTypeEntity;

@Component
public class RoleTypeToRoleTypeEntityConverter implements Converter<Role, RoleTypeEntity> {

    private final PermissionToPermissionEntityConverter permissionMapper;

    public RoleTypeToRoleTypeEntityConverter(PermissionToPermissionEntityConverter permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public RoleTypeEntity toEntity(Role fromSource) {
        if (fromSource == null) {
            throw new IllegalArgumentException("RoleType cannot be null");
        }

        Set<PermissionEntity> permissionEntities = fromSource.permissions().stream()
                .map(permissionMapper::toEntity).collect(Collectors.toSet());

        var roleTypeEntity = new RoleTypeEntity();
        roleTypeEntity.setPermissionEntities(permissionEntities);
        roleTypeEntity.setRoleTypeName(fromSource.displayName());

        return roleTypeEntity;
    }

    @Override
    public Role toDomain(RoleTypeEntity fromTarget) {
        if (fromTarget == null) {
            throw new IllegalArgumentException("RoleTypeEntity cannot be null");
        }

        Set<Permission> permissions = fromTarget.getPermissionEntities().stream()
                .map(permissionMapper::toDomain).collect(Collectors.toSet());

        return new Role(fromTarget.getRoleTypeName(), permissions);
    }
}