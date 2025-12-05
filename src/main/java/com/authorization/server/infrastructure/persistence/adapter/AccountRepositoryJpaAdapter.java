package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.authorization.server.application.exception.AccountPersistentException;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.contract.AccountRepositoryJpa;
import com.authorization.server.infrastructure.persistence.converter.AccountToAccountEntityConverter;

@Component
public class AccountRepositoryJpaAdapter implements AccountRepository {

    private final AccountRepositoryJpa accountRepositoryJpa;
    private final AccountToAccountEntityConverter accountConverter;

    public AccountRepositoryJpaAdapter(AccountRepositoryJpa accountRepositoryJpa, AccountToAccountEntityConverter accountConverter) {
        this.accountRepositoryJpa = accountRepositoryJpa;
        this.accountConverter = accountConverter;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> save(Account account) {

        try {
            Objects.requireNonNull(account);
            var accountEntity = accountRepositoryJpa.save(accountConverter.toEntity(account));
            accountRepositoryJpa.flush();

            return Optional.of(accountConverter.toDomain(accountEntity));

        } catch (DataIntegrityViolationException e) {
            throw new AccountPersistentException(e.getMessage());
        }
    }

    public long count() {
        return accountRepositoryJpa.count();
    }
}