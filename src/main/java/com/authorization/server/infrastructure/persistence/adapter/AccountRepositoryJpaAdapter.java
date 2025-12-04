package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.authorization.server.application.exception.AccountPersistentException;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.contract.AccountRepositoryJpa;
import com.authorization.server.infrastructure.persistence.converter.AccountToAccountEntityConverter;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.AccountEntity;

@Component
public class AccountRepositoryJpaAdapter implements AccountRepository {

    private final AccountRepositoryJpa jpa;
    private final AccountToAccountEntityConverter accountConverter;

    public AccountRepositoryJpaAdapter(AccountRepositoryJpa jpa, AccountToAccountEntityConverter accountConverter) {
        this.jpa = jpa;
        this.accountConverter = accountConverter;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> save(Account account) {
//        try {
//            AccountEntity accountEntity = jpa.save(accountConverter.toEntity(account));
//            jpa.flush();
//            return Optional.of(accountConverter.toDomain(accountEntity));
//        } catch (DataIntegrityViolationException e) {
//            throw new AccountPersistentException(e.getMessage());
//        }
        return Optional.empty();
    }
}