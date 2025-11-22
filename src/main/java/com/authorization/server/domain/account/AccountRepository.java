package com.authorization.server.domain.account;

public interface AccountRepository {

    Account findAccountByEmail(String email);
    Account findAccountByUsername(String username);
    boolean save(Account account);
}
