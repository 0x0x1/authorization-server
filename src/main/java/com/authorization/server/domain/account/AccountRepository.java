package com.authorization.server.domain.account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> save(Account account);
    void flush();
}
