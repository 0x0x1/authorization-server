package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.authorization.server.application.exception.AccountContraintViolationException;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.contract.AccountRepositoryJpa;
import com.authorization.server.infrastructure.persistence.converter.AccountToAccountEntityEntityConverter;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.AccountEntity;

@Component
public class AccountRepositoryJpaAdapter implements AccountRepository {

    private final AccountRepositoryJpa accountRepositoryJpa;
    private final AccountToAccountEntityEntityConverter accountConverter;

    public AccountRepositoryJpaAdapter(AccountRepositoryJpa accountRepositoryJpa, AccountToAccountEntityEntityConverter accountConverter) {
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
            AccountEntity accountEntity = accountConverter.toEntity(account);
            AccountEntity savedAccountEntity = accountRepositoryJpa.save(accountEntity);
            accountRepositoryJpa.flush();
            Account savedAccount = accountConverter.toDomain(savedAccountEntity);
            return Optional.of(savedAccount);

        } catch (DataIntegrityViolationException e) {
            throw new AccountContraintViolationException(e.getMessage());
        }
    }

    public long count() {
        return accountRepositoryJpa.count();
    }
}