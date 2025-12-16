package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.PermissionCommand;
import com.authorization.server.domain.Permission;

@Component
public class PermissionToPermissionCommand implements Converter<Permission, PermissionCommand> {
    @Override
    public PermissionCommand convert(Permission source) {
        return new PermissionCommand(source.displayName(), source.description());
    }
}
