package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.identity.Permission;
import com.authorization.server.infrastructure.persistence.entity.authorization.PermissionEntity;

@Component
public class PermissionToPermissionEntity implements Converter<Permission, PermissionEntity> {

    @Override
    public PermissionEntity convert(Permission source) {
        if (source == null) {
            return null;
        }

        var entity = new PermissionEntity();
        entity.setDisplayName(source.displayName());
        entity.setDescription(source.description());

        return entity;
    }
}