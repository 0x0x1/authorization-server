package com.authorization.server.infrastructure.persistence.jpa.contract;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authorization.server.infrastructure.persistence.jpa.entity.account.AccountEntity;

@Repository
public interface AccountRepositoryJpa extends JpaRepository<AccountEntity, UUID> {
}
