package com.authorization.server.infrastructure.persistence.mapper;

public interface Mapper <INPUT, OUTPUT> {

    OUTPUT convert(INPUT dataContainer);
    //INPUT reverse(OUTPUT dataContainer);
}
