package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.PermissionCommand;
import com.authorization.server.infrastructure.persistence.entity.authorization.PermissionEntity;

@Component
public class PermissionEntityToPermissionCommandResult implements Converter<PermissionEntity, PermissionCommand> {

    @Override
    public PermissionCommand convert(PermissionEntity source) {
        if (source == null) {
            return null;
        }

        return new PermissionCommand(source.getDisplayName(), source.getDescription());
    }
}
