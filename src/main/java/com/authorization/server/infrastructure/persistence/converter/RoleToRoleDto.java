package com.authorization.server.infrastructure.persistence.converter;

import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.identity.Permission;
import com.authorization.server.identity.Role;
import com.authorization.server.web.dto.PermissionDto;
import com.authorization.server.web.dto.RoleDto;

@Component
public class RoleToRoleDto implements Converter<Role, RoleDto> {

    private final Converter<Permission, PermissionDto> permissionConverter;

    public RoleToRoleDto(PermissionToPermissionDto permissionToPermissionDto) {
        this.permissionConverter = permissionToPermissionDto;
    }

    @Override
    public RoleDto convert(Role source) {
        if (source == null) {
            return null;
        }
        Collection<PermissionDto> permissionDtos = source.permissions().stream().map(permissionConverter::convert).toList();
        return new RoleDto(source.displayName(), source.description(),permissionDtos);
    }
}