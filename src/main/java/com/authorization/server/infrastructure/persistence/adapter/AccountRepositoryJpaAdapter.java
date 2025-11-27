package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.authorization.server.application.exception.DuplicateUserException;
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
            return Optional.of(Account);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            //throw new DuplicateUserException("User already exists in the database");
        }
        return Optional.empty();
    }

    @Override
    public void flush() {
        try {
            jpa.flush();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUserException("User already exists in the database");
        }
    }
}