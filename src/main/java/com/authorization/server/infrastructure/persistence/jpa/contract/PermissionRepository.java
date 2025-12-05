package com.authorization.server.infrastructure.persistence.jpa.contract;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.PermissionEntity;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {

    @Query("SELECT p FROM PermissionEntity p WHERE p.displayName IN :names")
    Set<PermissionEntity> findAllByNameIN(Collection<String> names);


}
