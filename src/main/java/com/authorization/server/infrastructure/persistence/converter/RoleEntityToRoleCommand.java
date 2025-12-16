package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.PermissionCommand;
import com.authorization.server.application.command.RoleCommand;
import com.authorization.server.infrastructure.persistence.entity.authorization.PermissionEntity;
import com.authorization.server.infrastructure.persistence.entity.authorization.RoleEntity;

@Component
public class RoleEntityToRoleCommand implements Converter<RoleEntity, RoleCommand> {

    private final Converter<PermissionEntity, PermissionCommand> permissionConverter;

    public RoleEntityToRoleCommand(Converter<PermissionEntity, PermissionCommand> permissionConverter) {
        this.permissionConverter = permissionConverter;
    }

    @Override
    public RoleCommand convert(RoleEntity source) {
        if (source == null) {
            return null;
        }

        return new RoleCommand(source.getDisplayName(),
                source.getDescription(),
                source.getPermissionEntities().stream()
                        .map(permissionConverter::convert).toList());
    }
}