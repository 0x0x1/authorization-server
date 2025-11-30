package com.authorization.server.infrastructure.persistence.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.authorization.server.domain.account.Account;
import com.authorization.server.domain.account.AccountState;
import com.authorization.server.domain.account.EmailAddress;
import com.authorization.server.domain.account.Permission;
import com.authorization.server.domain.account.RoleType;
import com.authorization.server.domain.account.Username;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.AccountEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.AccountStateEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.EmailAddressEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.PasswordEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.RoleTypeEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.UsernameEntity;

@Component
public class AccountToAccountEntityConverter implements Converter<Account, AccountEntity> {

    private final RoleTypeToRoleTypeEntityConverter roleTypeMapper;

    public AccountToAccountEntityConverter(RoleTypeToRoleTypeEntityConverter roleTypeMapper) {
        this.roleTypeMapper = roleTypeMapper;
    }

    @Override
    public AccountEntity convert(Account fromSource) {
        System.out.println(fromSource);
        if (fromSource == null || fromSource.getAccountState() == null) {
            throw new IllegalArgumentException("Account and it's state cannot be null");
        }

        var accountStateEntity = new AccountStateEntity();
        accountStateEntity.setAccountStatus(fromSource.getAccountState().getAccountStatus());
        accountStateEntity.setActivatedAt(fromSource.getAccountState().getActivatedAt());

        Set<RoleTypeEntity> roleTypeEntities = fromSource.getRoleTypes().stream()
                .map(roleTypeMapper::convert)
                .collect(Collectors.toSet());

        var accountEntity = new AccountEntity();
        accountEntity.setUsername(new UsernameEntity(fromSource.getUsername().getUsername()));
        accountEntity.setPassword(new PasswordEntity(fromSource.getPassword().getPassword()));
        accountEntity.setEmail(new EmailAddressEntity(fromSource.getEmail().getEmailAddress()));
        accountEntity.setAccountStateEntity(accountStateEntity);
        accountEntity.setRoleTypeEntities(roleTypeEntities);
        accountEntity.setIsAccountEnabled(false);
        accountEntity.setIsAccountPasswordExpired(false);

        return accountEntity;
    }

    @Override
    public Account reverse(AccountEntity fromTarget) {

        Set<RoleType> roleTypes = fromTarget.getRoleTypeEntities().stream()
                .map(roleTypeEntity -> {
                    // Get permissions for THIS roleTypes type
                    Set<Permission> permissions = roleTypeEntity.getPermissionEntities().stream()
                            .map(permissionEntity -> new Permission(
                                    permissionEntity.getPermissionName(),
                                    permissionEntity.getDescription()
                            ))
                            .collect(Collectors.toSet());

                    // Create RoleType with its permissions
                    return new RoleType(roleTypeEntity.getRoleTypeName(), null);
                })
                .collect(Collectors.toSet());

        var accountState = new AccountState(fromTarget.getAccountStateEntity().getAccountStatus(),
                fromTarget.getAccountStateEntity().getActivatedAt());
        PasswordEntity password = fromTarget.getPassword();

        return Account.builder()
                .id(fromTarget.getId())
                .username(new Username(fromTarget.getUsername().getUsername()))
                .email(new EmailAddress(fromTarget.getEmail().getEmail()))
                .accountState(accountState)// or whatever properties Account has
                .roleTypes(roleTypes)
                .build();

    }
}