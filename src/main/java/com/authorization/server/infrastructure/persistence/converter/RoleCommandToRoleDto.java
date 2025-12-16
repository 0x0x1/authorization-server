package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.PermissionCommand;
import com.authorization.server.application.command.RoleCommand;
import com.authorization.server.web.dto.PermissionDto;
import com.authorization.server.web.dto.RoleDto;

@Component
public class RoleCommandToRoleDto implements Converter<RoleCommand, RoleDto> {

    private final Converter<PermissionCommand, PermissionDto> permissionConverter;

    public RoleCommandToRoleDto(Converter<PermissionCommand, PermissionDto> permissionConverter) {
        this.permissionConverter = permissionConverter;
    }

    @Override
    public RoleDto convert(RoleCommand source) {
        if (source == null) {
            return null;
        }

        return new RoleDto(source.displayName(), source.description(), source.permissions().stream()
                .map(permissionConverter::convert).toList());
    }
}
