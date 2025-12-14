package com.authorization.server.infrastructure.persistence.jpa.converter.dto;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.Converter;
import com.authorization.server.identity.Role;
import com.authorization.server.web.dto.PermissionDto;
import com.authorization.server.web.dto.RoleDto;

@Component
public class RoleConverter implements Converter<Role, RoleDto> {

    private final PermissionConverter permissionConverter;

    public RoleConverter(PermissionConverter permissionConverter) {
        this.permissionConverter = permissionConverter;
    }

    @Override
    public RoleDto convert(Role source) {
        Objects.requireNonNull(source);
        Collection<PermissionDto> permissionDtos = source.permissions().stream().map(permissionConverter::convert).toList();
        return new RoleDto(source.displayName(), source.description(),permissionDtos);
    }
}
