package com.authorization.server.infrastructure.persistence.converter;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.authorization.server.identity.Account;
import com.authorization.server.identity.Permission;
import com.authorization.server.identity.Role;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.AccountEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.AccountStateEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.EmailAddressEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.PasswordEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleTypeEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.UsernameEntity;

@Component
public class AccountToAccountEntityConverter implements Converter<Account, AccountEntity> {

    private final RoleTypeToRoleTypeEntityConverter roleTypeMapper;

    public AccountToAccountEntityConverter(RoleTypeToRoleTypeEntityConverter roleTypeMapper) {
        this.roleTypeMapper = roleTypeMapper;
    }

    @Override
    public AccountEntity toEntity(Account fromSource) {
//        if (fromSource == null || fromSource.getAccountState() == null) {
//            throw new IllegalArgumentException("Account and it's state cannot be null");
//        }
//
//        var accountStateEntity = new AccountStateEntity();
//        accountStateEntity.setAccountLifecycleStatus(fromSource.getAccountState().getAccountStatus());
//        accountStateEntity.setActivatedAt(fromSource.getAccountState().getActivatedAt());
//
//        Set<RoleTypeEntity> roleTypeEntities = fromSource.getRoles().stream()
//                .map(roleTypeMapper::convert)
//                .collect(Collectors.toSet());
//
//        var accountEntity = new AccountEntity();
//        accountEntity.setUsername(new UsernameEntity(fromSource.getUsername().value()));
//        accountEntity.setPassword(new PasswordEntity(fromSource.getPassword().value()));
//        accountEntity.setEmail(new EmailAddressEntity(fromSource.getEmailAddress().emailAddress()));
//        accountEntity.setAccountStateEntity(accountStateEntity);
//        accountEntity.setRoleTypeEntities(roleTypeEntities);
//        accountEntity.setIsAccountEnabled(false);
//        accountEntity.setIsAccountPasswordExpired(false);
//
//        return accountEntity;
        return null;
    }

    @Override
    public Account toDomain(AccountEntity fromTarget) {

        Set<Role> roles = fromTarget.getRoleTypeEntities().stream()
                .map(roleTypeEntity -> {
                    // Get permissions for THIS roleTypes type
                    Set<Permission> permissions = roleTypeEntity.getPermissionEntities().stream()
                            .map(permissionEntity -> new Permission(
                                    permissionEntity.getPermissionName(),
                                    permissionEntity.getDescription()
                            ))
                            .collect(Collectors.toSet());

                    // Create RoleType with its permissions
                    return new Role(roleTypeEntity.getRoleTypeName(), null);
                })
                .collect(Collectors.toSet());

//        var accountState = new AccountState(fromTarget.getAccountStateEntity().getAccountLifecycleStatus(),
//                fromTarget.getAccountStateEntity().getActivatedAt());
//        PasswordEntity password = fromTarget.getPassword();

//        return Account.builder()
//                .id(fromTarget.getId())
//                .username(fromTarget.getUsername().getUsername())
//                .emailAddress(fromTarget.getEmail().getEmail())
//                .accountState(accountState)// or whatever properties Account has
//                .roleTypes(roleTypes)
//                .build();

        return null;

    }
}