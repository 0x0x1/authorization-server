package com.authorization.server.infrastructure.persistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.authorization.server.application.command.PermissionCommand;
import com.authorization.server.web.dto.PermissionDto;

@Component
public class PermissionCommandToPermissionDto implements Converter<PermissionCommand, PermissionDto> {

    @Override
    public PermissionDto convert(PermissionCommand source) {
        if (source == null) {
            return null;
        }

        return new PermissionDto(source.displayName(), source.description());
    }
}
