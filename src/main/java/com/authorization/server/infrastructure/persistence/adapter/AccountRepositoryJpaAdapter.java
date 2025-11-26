package com.authorization.server.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.authorization.server.application.exception.DuplicateUserException;
import com.authorization.server.domain.account.Account;
import com.authorization.server.domain.account.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.contract.AccountRepositoryJpa;
import com.authorization.server.infrastructure.persistence.mapper.AccountMapper;

@Component
public class AccountRepositoryJpaAdapter implements AccountRepository {

    private final AccountRepositoryJpa jpa;
    private final AccountMapper mapper;

    public AccountRepositoryJpaAdapter(AccountRepositoryJpa jpa, AccountMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Optional<Account> save(Account account) {
        try {
            var accountEntity = jpa.save(mapper.convert(account));
            return Optional.of(mapper.convertToDomain(accountEntity));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateUserException("User already exists in the database");
        }
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