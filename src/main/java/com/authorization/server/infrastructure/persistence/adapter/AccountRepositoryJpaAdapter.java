package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.authorization.server.application.exception.AccountPersistentException;
import com.authorization.server.domain.account.Account;
import com.authorization.server.domain.account.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.contract.AccountRepositoryJpa;
import com.authorization.server.infrastructure.persistence.converter.AccountToAccountEntityConverter;

@Component
public class AccountRepositoryJpaAdapter implements AccountRepository {

    private final AccountRepositoryJpa jpa;
    private final AccountToAccountEntityConverter converter;

    public AccountRepositoryJpaAdapter(AccountRepositoryJpa jpa, AccountToAccountEntityConverter converter) {
        this.jpa = jpa;
        this.converter = converter;
    }

    @Override
    public Optional<Account> save(Account account) {
        try {
            var accountEntity = converter.convert(account);
            var savedAccountEntity = jpa.save(accountEntity);
            var Account = converter.reverse(savedAccountEntity);
            jpa.flush();
            return Optional.of(Account);
        } catch (DataIntegrityViolationException e) {
            throw new AccountPersistentException(e.getMessage());
        }
    }
}