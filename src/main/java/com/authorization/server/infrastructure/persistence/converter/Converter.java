package com.authorization.server.infrastructure.persistence.converter;

public interface Converter<INPUT, OUTPUT> {

    OUTPUT convert(INPUT fromSource);
    INPUT reverse(OUTPUT fromTarget);
}