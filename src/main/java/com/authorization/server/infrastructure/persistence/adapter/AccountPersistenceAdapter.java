package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.authorization.server.application.exception.AccountContraintViolationException;
import com.authorization.server.domain.Account;
import com.authorization.server.domain.AccountFactoryPort;
import com.authorization.server.domain.AccountPersistencePort;
import com.authorization.server.infrastructure.fixture.PermissionSeed;
import com.authorization.server.infrastructure.persistence.DuplicateAccountException;
import com.authorization.server.infrastructure.persistence.constant.Jpa;
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
            checkForDuplicateAndThrow(e);
            throw new AccountContraintViolationException("Account data integrity violation");
        }
    }

    private Collection<RoleEntity> getRoleEntitiesFromRoleIds(Account account){
        return account.getRoleIds().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private void checkForDuplicateAndThrow(DataIntegrityViolationException e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        if (rootCause instanceof PSQLException ex) {
            String constraintName = Objects.requireNonNull(ex.getServerErrorMessage()).getConstraint();
            if (Jpa.Column.EMAIL_CONSTRAINT_NAME.equalsIgnoreCase(constraintName) || Jpa.Column.USERNAME_CONSTRAINT_NAME.equalsIgnoreCase(constraintName)) {
                throw new DuplicateAccountException("duplicate key: "+ constraintName, constraintName);
            }
        }}

    public long count() {
        return accountRepository.count();
    }
}