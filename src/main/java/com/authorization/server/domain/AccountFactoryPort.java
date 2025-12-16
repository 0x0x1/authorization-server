package com.authorization.server.domain;

import java.util.Collection;
import java.util.UUID;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.application.command.RegisterCommandResult;
import com.authorization.server.infrastructure.persistence.entity.authorization.RoleEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.AccountEntity;

public interface AccountFactoryPort {

    Account from(RegisterCommand source, Collection<UUID> roleIds);
    AccountEntity from(Account source, Collection<RoleEntity> roleEntities);
    RegisterCommandResult toRegisterCommandResult(Account source, Collection<Role> roleEntities);
}
