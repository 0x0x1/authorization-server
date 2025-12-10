package com.authorization.server.infrastructure.persistence.jpa.converter.entity;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.EntityConverter;
import com.authorization.server.identity.Credentials;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.CredentialsEntity;

/**
 * Converts between the domain {@link Credentials} value object and its
 * persistence-side representation {@link CredentialsEntity}.
 *
 * <p>This class belongs to the infrastructure layer, and it ensures that the
 * domain does not depend on JPA annotations or database-specific constraints.</p>
 *
 * <p>All invariant checks remain in the domain object. This converter is
 * responsible only for mapping values, not validating business rules.</p>
 */
@Component
public class CredentialsEntityConverter implements EntityConverter<Credentials, CredentialsEntity> {

    private final UsernameEntityConverter usernameConverter;
    private final PasswordEntityConverter passwordConverter;

    /**
     * Creates a new {@code CredentialsConverter} with the required component
     * converters for username and password value objects.
     *
     * @param usernameConverter the converter used to translate {@code Username}
     *                          value objects to and from their persistence entities;
     *                          must not be {@code null}
     * @param passwordConverter the converter used to translate {@code Password}
     *                          value objects to and from their persistence entities;
     *                          must not be {@code null}
     */
    public CredentialsEntityConverter(UsernameEntityConverter usernameConverter, PasswordEntityConverter passwordConverter) {
        this.usernameConverter = usernameConverter;
        this.passwordConverter = passwordConverter;
    }

    /**
     * Converts a domain {@link Credentials} value object into its JPA
     * persistence representation {@link CredentialsEntity}.
     *
     * <p>A {@code null} input is not allowed. Null-checking is performed at the
     * boundary since conversion happens in the infrastructure layer, outside of
     * domain control.</p>
     *
     * @param fromSource the domain username value object
     * @return a new {@link CredentialsEntity} containing the object representation
     * @throws NullPointerException if {@code fromSource} is null
     */
    @Override
    public CredentialsEntity toEntity(Credentials fromSource) {
        Objects.requireNonNull(fromSource);

        var usernameEntity = usernameConverter.toEntity(fromSource.username());
        var passwordEntity = passwordConverter.toEntity(fromSource.password());

        return new CredentialsEntity(usernameEntity, passwordEntity);
    }

    /**
     * Converts a persistence {@link CredentialsEntity} into the domain value object
     * {@link Credentials}. Domain constructor invariants are enforced inside the
     * {@code Username} class itself.
     *
     * <p>A {@code null} input is not allowed. Null-checking here protects the
     * domain from receiving invalid or incomplete data from persistence.</p>
     *
     * @param fromTarget the persistence-side Credentials object
     * @return a domain {@link Credentials} instance created from the stored object
     * @throws NullPointerException if {@code fromTarget} is null
     */
    @Override
    public Credentials toDomain(CredentialsEntity fromTarget) {
        Objects.requireNonNull(fromTarget);

        var username = usernameConverter.toDomain(fromTarget.getUsername());
        var password = passwordConverter.toDomain(fromTarget.getPassword());

        return new Credentials(username, password);
    }
}