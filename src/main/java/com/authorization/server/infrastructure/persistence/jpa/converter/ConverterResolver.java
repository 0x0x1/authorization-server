package com.authorization.server.infrastructure.persistence.jpa.converter;

import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import com.authorization.server.application.port.outbound.Converter;
import com.authorization.server.infrastructure.persistence.jpa.TypeResolutionException;

/**
 * Utility class for resolving generic type parameters from Converter implementations.
 */
@Service
public class ConverterResolver {

    /**
     * Resolves the source and target types for a given converter.
     *
     * @param converter the converter instance
     * @return a TypePair containing source and target types
     * @throws TypeResolutionException if types cannot be resolved
     */
    public ConversionKey resolveTypes(Converter<?, ?> converter) {
        Class<?>[] typeArguments = GenericTypeResolver.resolveTypeArguments(
                converter.getClass(),
                Converter.class);

        validateTypeArguments(typeArguments, converter);

        Class<?> source = typeArguments[0];
        Class<?> target = typeArguments[1];

        validateResolvedTypes(source, target, converter);

        return new ConversionKey(source, target);
    }

    private void validateTypeArguments(Class<?>[] typeArguments, Converter<?, ?> converter) {
        if (typeArguments == null || typeArguments.length != 2) {
            throw new TypeResolutionException(String.format(
                    "Unable to resolve generic type arguments for converter: %s. " +
                            "Ensure the converter implements Converter<S, T> with concrete types.",
                    converter.getClass().getName()));
        }
    }

    private void validateResolvedTypes(Class<?> source, Class<?> target, Converter<?, ?> converter) {
        if (source == null || target == null) {
            throw new TypeResolutionException(String.format(
                    "Source (%s) or target (%s) type is null for converter: %s. " +
                            "This typically occurs with type erasure or when using raw types.",
                    source, target, converter.getClass().getName()));
        }
    }
}