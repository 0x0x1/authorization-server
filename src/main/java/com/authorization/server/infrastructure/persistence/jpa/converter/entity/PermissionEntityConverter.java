package com.authorization.server.infrastructure.persistence.jpa.converter.entity;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.EntityConverter;
import com.authorization.server.identity.Permission;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.PermissionEntity;

/**
 * Converts between the domain {@link Permission} value object and its
 * persistence-side representation {@link PermissionEntity}.
 *
 * <p>This class belongs to the infrastructure layer, and it ensures that the
 * domain does not depend on JPA annotations or database-specific constraints.</p>
 *
 * <p>All invariant checks remain in the domain object. This converter is
 * responsible only for mapping values, not validating business rules.</p>
 */
@Component
public class PermissionEntityConverter implements EntityConverter<Permission, PermissionEntity> {

    /**
     * Converts a {@link Permission} domain object into a {@link PermissionEntity} persistence entity.
     * <p>
     * This method does not persist the entity; it only performs a pure data mapping.
     * Null values are not allowed.
     *
     * @param fromSource the {@link Permission} domain object to convert; must not be {@code null}
     * @return a new {@link PermissionEntity} representing the given domain permission
     * @throws NullPointerException if {@code fromSource} is {@code null}
     */
    @Override
    public PermissionEntity toEntity(Permission fromSource) {
        Objects.requireNonNull(fromSource, "Permission to convert cannot be null");

        var entity = new PermissionEntity();
        entity.setDisplayName(fromSource.displayName());
        entity.setDescription(fromSource.description());

        return entity;
    }

    /**
     * Converts a {@link PermissionEntity} persistence entity into a {@link Permission} domain object.
     * <p>
     * The {@code id} of the entity is preserved in the domain object.
     * Null values are not allowed.
     *
     * @param fromTarget the {@link PermissionEntity} to convert; must not be {@code null}
     * @return a new {@link Permission} representing the given persistence entity
     * @throws NullPointerException if {@code fromTarget} is {@code null}
     */
    @Override
    public Permission toDomain(PermissionEntity fromTarget) {
        Objects.requireNonNull(fromTarget, "PermissionEntity to convert cannot be null");

        return new Permission(
                fromTarget.getId(),
                fromTarget.getDisplayName(),
                fromTarget.getDescription()
        );
    }
}