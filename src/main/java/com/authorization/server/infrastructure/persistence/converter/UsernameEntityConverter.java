package com.authorization.server.infrastructure.persistence.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.EntityConverter;
import com.authorization.server.identity.Username;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.UsernameEntity;

/**
 * Converts between the domain {@link Username} value object and its
 * persistence-side representation {@link UsernameEntity}.
 *
 * <p>This class belongs to the infrastructure layer, and it ensures that the
 * domain does not depend on JPA annotations or database-specific constraints.</p>
 *
 * <p>All invariant checks remain in the domain object. This converter is
 * responsible only for mapping values, not validating business rules.</p>
 */
@Component
public class UsernameEntityConverter implements EntityConverter<Username, UsernameEntity> {

    /**
     * Converts a domain {@link Username} value object into its JPA
     * persistence representation {@link UsernameEntity}.
     *
     * <p>A {@code null} input is not allowed. Null-checking is performed at the
     * boundary since conversion happens in the infrastructure layer, outside of
     * domain control.</p>
     *
     * @param fromSource the domain username value object
     * @return a new {@link UsernameEntity} containing the primitive representation
     * @throws NullPointerException if {@code fromSource} is null
     */
    @Override
    public UsernameEntity toEntity(Username fromSource) {
        Objects.requireNonNull(fromSource, "Username (domain) cannot be null");
        return new UsernameEntity(fromSource.username());
    }

    /**
     * Converts a persistence {@link UsernameEntity} into the domain value object
     * {@link Username}. Domain constructor invariants are enforced inside the
     * {@code Username} class itself.
     *
     * <p>A {@code null} input is not allowed. Null-checking here protects the
     * domain from receiving invalid or incomplete data from persistence.</p>
     *
     * @param fromTarget the persistence-side username value
     * @return a domain {@link Username} instance created from the stored value
     * @throws NullPointerException if {@code fromTarget} is null
     */
    @Override
    public Username toDomain(UsernameEntity fromTarget) {
        Objects.requireNonNull(fromTarget, "UsernameEntity (persistence) cannot be null");
        return new Username(fromTarget.getUsername());
    }
}