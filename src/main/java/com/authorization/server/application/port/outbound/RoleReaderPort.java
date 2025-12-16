package com.authorization.server.application.port.outbound;

import java.util.Collection;
import java.util.UUID;

import com.authorization.server.domain.Account;
import com.authorization.server.domain.Role;

public interface RoleReaderPort {

    Collection<UUID> retrieveRoleIdsByDisplayName(Collection<String> DisplayNames);
    Collection<Role> retrieveRolesByIds(Account source, Collection<UUID> roleIds);
}
