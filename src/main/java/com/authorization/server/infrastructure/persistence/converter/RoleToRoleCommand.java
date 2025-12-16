package com.authorization.server.infrastructure.persistence.converter;

import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.PermissionCommand;
import com.authorization.server.application.command.RoleCommand;
import com.authorization.server.identity.Permission;
import com.authorization.server.identity.Role;

@Component
public class RoleToRoleCommand implements Converter<Role, RoleCommand> {

    private final Converter<Permission, PermissionCommand> permissionConverter;

    public RoleToRoleCommand(Converter<Permission, PermissionCommand> permissionConverter) {
        this.permissionConverter = permissionConverter;
    }

    @Override
    public RoleCommand convert(Role source) {
        if (source == null) {
            return null;
        }

        Collection<PermissionCommand> result = source.permissions().stream()
                .map(permissionConverter::convert)
                .toList();

        return new RoleCommand(source.displayName(),
                source.description(), result);
    }
}