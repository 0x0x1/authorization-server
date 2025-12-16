package com.authorization.server.domain;

import java.util.Optional;

/*
 * Domain-Persistence boundary. Describes application capability on domain level.
 */
public interface AccountPersistencePort {

    /*
     * Traverse the Account table and get an account for the given username.
     */
    Optional<Account> findByUsername(String username);

    /*
     * Save an account object to the table.
     */
    Account save(Account account);

    long count();
}