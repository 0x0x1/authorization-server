package com.authorization.server.infrastructure.persistence.jpa.contract;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleTypeEntity;

@Repository
public interface RoleTypeRepository extends JpaRepository<RoleTypeEntity, UUID> {

    RoleTypeEntity findByRoleTypeName(String roleTypeName);
}
