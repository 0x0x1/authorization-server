package com.authorization.server.application.port.outbound;

import java.util.List;

@FunctionalInterface
public interface Converter<S, T> {

    T convert(S source);

    default List<T> convertAll(List<S> s) {
        return s.stream().map(this::convert).toList();
    }
}