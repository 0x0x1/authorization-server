package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.authorization.server.application.exception.AccountContraintViolationException;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.converter.AccountFactory;
import com.authorization.server.infrastructure.persistence.entity.authorization.RoleEntity;
import com.authorization.server.infrastructure.persistence.repository.AccountRepositoryJpa;
import com.authorization.server.infrastructure.persistence.repository.RoleRepository;

@Component
public class AccountRepositoryJpaAdapter implements AccountRepository {

    private final AccountRepositoryJpa accountRepositoryJpa;
    private final ConversionService conversionService;
    private final RoleRepository roleRepository;
    private final AccountFactory accountFactory;

    public AccountRepositoryJpaAdapter(AccountRepositoryJpa accountRepositoryJpa, ConversionService conversionService, RoleRepository roleRepository, AccountFactory accountFactory) {
        this.accountRepositoryJpa = accountRepositoryJpa;
        this.conversionService = conversionService;
        this.roleRepository = roleRepository;
        this.accountFactory = accountFactory;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> save(Account account) {
        try {
            var accountEntity = accountFactory.from(account, getRoleEntitiesFromRoleIds(account));
            var savedAccountEntity = accountRepositoryJpa.save(accountEntity);
            accountRepositoryJpa.flush();
            var savedAccount = conversionService.convert(savedAccountEntity, Account.class);
            return Optional.ofNullable(savedAccount);
        } catch (DataIntegrityViolationException e) {
            throw new AccountContraintViolationException(e.getMessage());
        }
    }

    private Collection<RoleEntity> getRoleEntitiesFromRoleIds(Account account){
        return account.getRoleIds().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public long count() {
        return accountRepositoryJpa.count();
    }
}