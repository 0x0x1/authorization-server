package com.authorization.server.infrastructure.persistence.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.EntityConverter;
import com.authorization.server.identity.EmailAddress;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.EmailAddressEntity;

/**
 * Converts between the domain {@link EmailAddress} value object and its
 * persistence-side representation {@link EmailAddressEntity}.
 *
 * <p>This class belongs to the infrastructure layer, and it ensures that the
 * domain does not depend on JPA annotations or database-specific constraints.</p>
 *
 * <p>All invariant checks remain in the domain object. This converter is
 * responsible only for mapping values, not validating business rules.</p>
 */
@Component
public class EmailAddressEntityConverter implements EntityConverter<EmailAddress, EmailAddressEntity> {

    /**
     * Converts a domain {@link EmailAddress} value object into its JPA
     * persistence representation {@link EmailAddressEntity}.
     *
     * <p>A {@code null} input is not allowed. Null-checking is performed at the
     * boundary since conversion happens in the infrastructure layer, outside of
     * domain control.</p>
     *
     * @param fromSource the domain username value object
     * @return a new {@link EmailAddressEntity} containing the primitive representation
     * @throws NullPointerException if {@code fromSource} is null
     */
    @Override
    public EmailAddressEntity toEntity(EmailAddress fromSource) {
        Objects.requireNonNull(fromSource);
        return new EmailAddressEntity(fromSource.emailAddress());
    }

    /**
     * Converts a persistence {@link EmailAddressEntity} into the domain value object
     * {@link EmailAddress}. Domain constructor invariants are enforced inside the
     * {@code Username} class itself.
     *
     * <p>A {@code null} input is not allowed. Null-checking here protects the
     * domain from receiving invalid or incomplete data from persistence.</p>
     *
     * @param fromTarget the persistence-side username value
     * @return a domain {@link EmailAddress} instance created from the stored value
     * @throws NullPointerException if {@code fromTarget} is null
     */
    @Override
    public EmailAddress toDomain(EmailAddressEntity fromTarget) {
        Objects.requireNonNull(fromTarget);
        return new EmailAddress(fromTarget.getEmail());
    }
}