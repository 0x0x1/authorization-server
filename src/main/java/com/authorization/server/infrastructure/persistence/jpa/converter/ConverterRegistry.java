package com.authorization.server.infrastructure.persistence.jpa.converter;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.authorization.server.application.port.outbound.Converter;
import com.authorization.server.infrastructure.persistence.jpa.UnsupportedConversion;
import com.authorization.server.infrastructure.persistence.jpa.TypeResolutionException;

/**
 * Central registry for application {@link Converter} implementations.
 * <p>
 * This registry automatically discovers all {@link Converter} beans from the
 * Spring {@link ApplicationContext} at startup and registers them using a
 * {@link ConversionKey} derived from their generic source and target types.
 * </p>
 *
 * <h2>Lifecycle</h2>
 * <ul>
 *   <li>All {@link Converter} beans are discovered during {@link PostConstruct}.</li>
 *   <li>Converters are registered exactly once per application context.</li>
 *   <li>If a converter's generic types cannot be resolved, application startup fails.</li>
 * </ul>
 *
 * <h2>Thread Safety</h2>
 * <p>
 * This class is thread-safe. Converters are stored in a {@link ConcurrentHashMap}
 * and lookups are lock-free after initialization.
 * </p>
 *
 * <h2>Type Safety</h2>
 * <p>
 * The public API is fully type-safe for callers. Internally, an unchecked cast
 * is required due to Java type erasure when retrieving converters from the registry.
 * This cast is safe because all converters are registered using a
 * {@link ConversionKey} derived from their declared generic types.
 * </p>
 *
 * <h2>Error Handling</h2>
 * <ul>
 *   <li>Missing converters result in {@link UnsupportedConversion}.</li>
 *   <li>Invalid or misconfigured converters result in {@link TypeResolutionException}
 *       during application startup.</li>
 * </ul>
 *
 *
 */
@Component
public class ConverterRegistry{

    private static final Logger log = LoggerFactory.getLogger(ConverterRegistry.class);

    private final Map<ConversionKey, Converter<?,?>> registry;
    private final ConverterResolver resolver;
    private final ApplicationContext context;

    public ConverterRegistry(ConverterResolver resolver, ApplicationContext context) throws BeansException {
        this.resolver = resolver;
        this.context = context;
        this.registry = new ConcurrentHashMap<>();
        registerConverters();
    }

    /**
     * Registers a converter for the given conversion key.
     *
     * @param key the conversion key representing source and target types
     * @param converter the converter implementation to register
     * @throws NullPointerException if the converter is {@code null}
     */
    public void register(ConversionKey key, Converter<?, ?> converter) {
        Objects.requireNonNull(converter, converter.getClass().getSimpleName() + " must not be null");
        var existing = registry.put(key, converter);

        if (existing != null) {
            log.warn("Replacing existing converter for {} -> {} (old: {}, new: {})",
                    key.source().getSimpleName(), key.target().getSimpleName(),
                    existing.getClass().getSimpleName(), converter.getClass().getSimpleName());
        } else {
            log.debug("Registered converter: {} -> {} using {}",
                    key.source().getSimpleName(), key.target().getSimpleName(), converter.getClass().getSimpleName());
        }
    }

    public <S, T> T convert(S source, Class<T> target) {
        Objects.requireNonNull(source, "Source must not be null");
        Objects.requireNonNull(target, "Target must not be null");
        ConversionKey key = ConversionKey.of(source.getClass(), target);

        if (!supportsConversion(key)) {
            throw new UnsupportedConversion(String.format(
                    "No converter registered for %s -> %s. Available converters: %s",
                    source.getClass().getName(),
                    target.getName(),
                    getAvailableConverters()));
        }

        @SuppressWarnings("unchecked")
        Converter<S, T> converter = (Converter<S, T>) registry.get(key);

        return converter.convert(source);
    }

    public void registerConverters() {
        Map<String, Converter> converters = context.getBeansOfType(Converter.class);
        try {
            converters.values().forEach( converter ->
                            register((resolver.resolveTypes(converter)), converter));
        } catch (TypeResolutionException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public boolean supportsConversion(ConversionKey key) {
        return registry.containsKey(key);
    }

    /**
     * Returns a string representation of all available conversions.
     */
    public String getAvailableConverters() {
        return registry.keySet().stream()
                .map(ConversionKey::toString)
                .sorted()
                .reduce((a, b) -> a + ", " + b)
                .orElse("none converters available");
    }

    /**
     * Returns the number of registered converters.
     */
    public int getConverterCount() {
        return registry.size();
    }
}