package com.authorization.server.infrastructure.persistence.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.authorization.server.domain.account.Account;
import com.authorization.server.domain.account.AccountState;
import com.authorization.server.domain.account.Permission;
import com.authorization.server.domain.account.RoleType;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.AccountEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.AccountStateEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.RoleTypeEntity;

@Component
public class AccountMapper implements Mapper<Account, AccountEntity> {

    private final RoleTypeMapper roleTypeMapper;

    public AccountMapper(RoleTypeMapper roleTypeMapper) {
        this.roleTypeMapper = roleTypeMapper;
    }

    @Override
    public AccountEntity convert(Account dataContainer) {
        if (dataContainer == null || dataContainer.getAccountState() == null) {
            throw new IllegalArgumentException("Account and it's state cannot be null");
        }

        var accountStateEntity = new AccountStateEntity();
        accountStateEntity.setAccountStatus(dataContainer.getAccountState().getAccountStatus());
        accountStateEntity.setActivatedAt(dataContainer.getAccountState().getActivatedAt());

        Set<RoleTypeEntity> roleTypeEntities = dataContainer.getRoleTypes().stream()
                .map(roleTypeMapper::convert)
                .collect(Collectors.toSet());

        var accountEntity = new AccountEntity();
        accountEntity.setUsername(dataContainer.getUsername());
        accountEntity.setPassword(dataContainer.getPassword());
        accountEntity.setEmail(dataContainer.getEmail());
        accountEntity.setAccountStateEntity(accountStateEntity);
        accountEntity.setRoleTypeEntities(roleTypeEntities);

        return accountEntity;
    }

    public Account convertToDomain(AccountEntity accountEntity) {

        Set<RoleType> roleTypes = accountEntity.getRoleTypeEntities().stream()
                .map(roleTypeEntity -> {
                    // Get permissions for THIS role type
                    Set<Permission> permissions = roleTypeEntity.getPermissionEntities().stream()
                            .map(permissionEntity -> new Permission(
                                    permissionEntity.getName(),
                                    permissionEntity.getDescription()
                            ))
                            .collect(Collectors.toSet());

                    // Create RoleType with its permissions
                    return new RoleType(roleTypeEntity.getDisplayName(), permissions);
                })
                .collect(Collectors.toSet());

        var accountState = new AccountState(accountEntity.getAccountStateEntity().getAccountStatus(),
                accountEntity.getAccountStateEntity().getActivatedAt());

        return Account.builder()
                .id(accountEntity.getId())
                .username(accountEntity.getUsername())
                .email(accountEntity.getEmail())
                .accountState(accountState)// or whatever properties Account has
                .roleTypes(roleTypes)
                .build();

    }
}