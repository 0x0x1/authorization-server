package com.authorization.server.infrastructure.persistence.converter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.EntityConverter;
import com.authorization.server.identity.Permission;
import com.authorization.server.identity.Role;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.PermissionEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleEntity;

/**
 * Converts between the domain {@link Role} value object and its
 * persistence-side representation {@link RoleEntity}.
 *
 * <p>This class belongs to the infrastructure layer, and it ensures that the
 * domain does not depend on JPA annotations or database-specific constraints.</p>
 *
 * <p>All invariant checks remain in the domain object. This converter is
 * responsible only for mapping values, not validating business rules.</p>
 */
@Component
public class RoleEntityConverter implements EntityConverter<Role, RoleEntity> {

    private final PermissionEntityConverter permissionConverter;

    /**
     * Constructs a {@link RoleEntityConverter} with the specified {@link PermissionEntityConverter}.
     *
     * @param permissionConverter converter to map between {@link Permission} domain objects
     * and {@link PermissionEntity} entities
     */
    public RoleEntityConverter(PermissionEntityConverter permissionConverter) {
        this.permissionConverter = permissionConverter;
    }

    /**
     * Converts a domain {@link Role} value object into its JPA
     * persistence representation {@link RoleEntity}.
     *
     * <p>A {@code null} input is not allowed. Null-checking is performed at the
     * boundary since conversion happens in the infrastructure layer, outside of
     * domain control.</p>
     *
     * @param fromSource the domain username value object
     * @return a new {@link RoleEntity} containing the primitive representation
     * @throws NullPointerException if {@code fromSource} is null
     */
    @Override
    public RoleEntity toEntity(Role fromSource) {
        Objects.requireNonNull(fromSource, "Role to convert cannot be null");

        List<PermissionEntity> permissionEntities = fromSource.permissions().stream()
                .map(permissionConverter::toEntity)
                .toList();

        var entity = new RoleEntity();
        entity.setDisplayName(fromSource.displayName());
        entity.setDescription(fromSource.description());
        entity.setPermissionEntities(permissionEntities);

        return entity;
    }

    /**
     * Converts a persistence {@link RoleEntity} into the domain value object
     * {@link Role}. Domain constructor invariants are enforced inside the
     * {@code Role} class itself.
     *
     * <p>A {@code null} input is not allowed. Null-checking here protects the
     * domain from receiving invalid or incomplete data from persistence.</p>
     *
     * @param fromTarget the persistence-side username value
     * @return a domain {@link Role} instance created from the stored value
     * @throws NullPointerException if {@code fromTarget} is null
     */
    @Override
    public Role toDomain(RoleEntity fromTarget) {
        Objects.requireNonNull(fromTarget, "RoleEntity to convert cannot be null");

        Collection<Permission> permissions = fromTarget.getPermissionEntities().stream()
                .map(permissionConverter::toDomain)
                .collect(Collectors.toSet());

        return new Role(
                fromTarget.getId(),
                fromTarget.getDisplayName(),
                fromTarget.getDescription(),
                permissions
        );
    }
}