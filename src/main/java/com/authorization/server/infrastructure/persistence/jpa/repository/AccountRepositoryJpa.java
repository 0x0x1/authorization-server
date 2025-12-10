package com.authorization.server.infrastructure.persistence.jpa.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authorization.server.infrastructure.persistence.jpa.entity.identity.AccountEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.EmailAddressEntity;

@Repository
public interface AccountRepositoryJpa extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByEmailAddress(EmailAddressEntity email);

}
