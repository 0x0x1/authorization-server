package com.authorization.server.infrastructure.persistence.jpa.converter.entity;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.EntityConverter;
import com.authorization.server.identity.Password;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.PasswordEntity;

/**
 * Converts between the domain {@link Password} value object and its
 * persistence-side representation {@link PasswordEntity}.
 *
 * <p>This class belongs to the infrastructure layer, and it ensures that the
 * domain does not depend on JPA annotations or database-specific constraints.</p>
 *
 * <p>All invariant checks remain in the domain object. This converter is
 * responsible only for mapping values, not validating business rules.</p>
 */
@Component
public class PasswordEntityConverter implements EntityConverter<Password, PasswordEntity> {

    /**
     * Converts a domain {@link Password} value object into its JPA
     * persistence representation {@link PasswordEntity}.
     *
     * <p>A {@code null} input is not allowed. Null-checking is performed at the
     * boundary since conversion happens in the infrastructure layer, outside of
     * domain control.</p>
     *
     * @param fromSource the domain username value object
     * @return a new {@link PasswordEntity} containing the primitive representation
     * @throws NullPointerException if {@code fromSource} is null
     */
    @Override
    public PasswordEntity toEntity(Password fromSource) {
        return new PasswordEntity(fromSource.password());
    }

    /**
     * Converts a persistence {@link PasswordEntity} into the domain value object
     * {@link Password}. Domain constructor invariants are enforced inside the
     * {@code Username} class itself.
     *
     * <p>A {@code null} input is not allowed. Null-checking here protects the
     * domain from receiving invalid or incomplete data from persistence.</p>
     *
     * @param fromTarget the persistence-side username value
     * @return a domain {@link Password} instance created from the stored value
     * @throws NullPointerException if {@code fromTarget} is null
     */
    @Override
    public Password toDomain(PasswordEntity fromTarget) {
        return new Password(fromTarget.getPassword());
    }
}