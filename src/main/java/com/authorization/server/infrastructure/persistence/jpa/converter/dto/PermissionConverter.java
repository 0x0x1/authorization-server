package com.authorization.server.infrastructure.persistence.jpa.converter.dto;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.Converter;
import com.authorization.server.identity.Permission;
import com.authorization.server.web.dto.PermissionDto;

@Component
public class PermissionConverter implements Converter<Permission, PermissionDto> {
    @Override
    public PermissionDto convert(Permission source) {
        Objects.requireNonNull(source);
        return new PermissionDto(source.displayName(), source.description());
    }
}
