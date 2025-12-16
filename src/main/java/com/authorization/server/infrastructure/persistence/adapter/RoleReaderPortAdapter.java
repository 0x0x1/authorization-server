package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.authorization.server.application.port.outbound.RoleReaderPort;
import com.authorization.server.domain.Account;
import com.authorization.server.domain.Role;
import com.authorization.server.infrastructure.persistence.entity.BaseEntity;
import com.authorization.server.infrastructure.persistence.repository.RoleRepository;

@Service
public class RoleReaderPortAdapter implements RoleReaderPort {

    private final ConversionService conversionService;
    private final RoleRepository roleRepository;

    public RoleReaderPortAdapter(ConversionService conversionService, RoleRepository roleRepository) {
        this.conversionService = conversionService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Collection<UUID> retrieveRoleIdsByDisplayName(Collection<String> displayNames) {
        return displayNames.stream().map(roleRepository::findByDisplayName).map(BaseEntity::getId).toList();
    }

    @Override
    public Collection<Role> retrieveRolesByIds(Account source, Collection<UUID> roleIds) {
        return source.getRoleIds().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(roleEntity -> conversionService.convert(roleEntity, Role.class))
                .toList();
    }
}