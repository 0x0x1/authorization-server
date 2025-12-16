package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.domain.Permission;
import com.authorization.server.web.dto.PermissionDto;

@Component
public class PermissionToPermissionDto implements Converter<Permission, PermissionDto> {

    @Override
    public PermissionDto convert(Permission source) {
        if (source == null) {
            return null;
        }
        return new PermissionDto(source.displayName(), source.description());
    }
}
