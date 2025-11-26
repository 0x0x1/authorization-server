package com.authorization.server.infrastructure.persistence.jpa.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authorization.server.domain.account.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.entity.account.AccountEntity;

@Repository
public interface AccountRepositoryJpa extends JpaRepository<AccountEntity, Long> {
}
