package com.authorization.server.application.port.outbound;

@FunctionalInterface
public interface DtoConverter<INPUT, OUTPUT> {

    OUTPUT convert(INPUT input);
}
