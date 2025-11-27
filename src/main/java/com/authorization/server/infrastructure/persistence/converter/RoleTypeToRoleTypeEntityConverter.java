package com.authorization.server.infrastructure.persistence.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.authorization.server.domain.account.RoleType;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.PermissionEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.RoleTypeEntity;

@Component
public class RoleTypeToRoleTypeEntityConverter implements Converter<RoleType, RoleTypeEntity> {

    private final PermissionToPermissionEntityConverter permissionMapper;

    public RoleTypeToRoleTypeEntityConverter(PermissionToPermissionEntityConverter permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public RoleTypeEntity convert(RoleType fromSource) {
        if (fromSource == null) {
            throw new IllegalArgumentException("RoleType cannot be null");
        }

        Set<PermissionEntity> permissionEntities = fromSource.getPermissions().stream()
                .map(permissionMapper::convert).collect(Collectors.toSet());

        var roleTypeEntity = new RoleTypeEntity();
        roleTypeEntity.setPermissionEntities(permissionEntities);
        roleTypeEntity.setRoleTypeName(fromSource.getRoleTypeName());

        return roleTypeEntity;
    }

    @Override
    public RoleType reverse(RoleTypeEntity fromTarget) {
        throw new UnsupportedOperationException();
    }
}