package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Collection;
import java.util.Optional;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.authorization.server.application.exception.AccountContraintViolationException;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountFactoryPort;
import com.authorization.server.identity.AccountPersistencePort;
import com.authorization.server.infrastructure.persistence.entity.authorization.RoleEntity;
import com.authorization.server.infrastructure.persistence.repository.AccountRepository;
import com.authorization.server.infrastructure.persistence.repository.RoleRepository;

@Component
public class AccountPersistenceAdapter implements AccountPersistencePort {

    private final AccountRepository accountRepository;
    private final ConversionService conversionService;
    private final RoleRepository roleRepository;
    private final AccountFactoryPort accountFactoryConverterPort;

    public AccountPersistenceAdapter(AccountRepository accountRepository, ConversionService conversionService, RoleRepository roleRepository, AccountFactoryPort accountFactoryConverterPort) {
        this.accountRepository = accountRepository;
        this.conversionService = conversionService;
        this.roleRepository = roleRepository;
        this.accountFactoryConverterPort = accountFactoryConverterPort;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Account save(Account account) {
        try {
            var accountEntity = accountFactoryConverterPort.from(account, getRoleEntitiesFromRoleIds(account));
            var savedAccountEntity = accountRepository.save(accountEntity);
            accountRepository.flush();
            return conversionService.convert(savedAccountEntity, Account.class);
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
        return accountRepository.count();
    }
}