package com.authorization.server.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authorization.server.infrastructure.persistence.entity.identity.AccountEntity;
import com.authorization.server.infrastructure.persistence.entity.identity.EmailAddressEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByEmailAddress(EmailAddressEntity email);

}
