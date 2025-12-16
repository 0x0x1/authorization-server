package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.domain.Permission;
import com.authorization.server.infrastructure.persistence.entity.authorization.PermissionEntity;

@Component
public class PermissionEntityToPermission implements Converter<PermissionEntity, Permission> {
    @Override
    public Permission convert(PermissionEntity source) {
        if (source == null) {
            return null;
        }

        return new Permission(source.getId(), source.getDisplayName(), source.getDescription());
    }
}
