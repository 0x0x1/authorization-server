package com.authorization.server.infrastructure.persistence.converter;

/**
 * A generic bidirectional converter between a domain model type and its
 * corresponding persistence entity type.
 * <p>
 * Implementations of this interface act as simple adapters that translate
 * between the domain layer and the persistence layer, ensuring that the two
 * models remain decoupled.
 *
 * @param <DOMAIN> the domain model type
 * @param <ENTITY> the persistence entity type
 */
public interface Converter<DOMAIN, ENTITY> {

    /**
     * Converts a domain model object into its corresponding persistence
     * entity representation.
     *
     * @param fromSource the domain object to convert; must not be {@code null}
     * @return the converted persistence entity
     */
    ENTITY toEntity(DOMAIN fromSource);

    /**
     * Converts a persistence entity into its corresponding domain model
     * representation.
     *
     * @param fromTarget the entity object to convert; must not be {@code null}
     * @return the converted domain model object
     */
    DOMAIN toDomain(ENTITY fromTarget);
}