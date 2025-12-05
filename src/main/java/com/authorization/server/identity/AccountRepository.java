package com.authorization.server.identity;

import java.util.Optional;

import com.authorization.server.infrastructure.persistence.jpa.entity.identity.AccountEntity;

/*
 * Domain-Persistence boundary. Describes application capability on domain level.
 */
public interface AccountRepository {

    /*
     * Traverse the Account table and get an account for the given username.
     */
    Optional<Account> findByUsername(String username);

    /*
     * Save an account object to the table.
     */
    Optional<AccountEntity> save(AccountEntity account);

    long count();
}