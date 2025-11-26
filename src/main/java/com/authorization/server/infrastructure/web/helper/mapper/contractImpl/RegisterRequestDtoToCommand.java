package com.authorization.server.infrastructure.web.helper.mapper.contractImpl;

import org.springframework.stereotype.Component;

import com.authorization.server.application.dto.RegisterCommand;
import com.authorization.server.infrastructure.persistence.mapper.Mapper;
import com.authorization.server.infrastructure.web.payload.RegisterRequestDto;

@Component
public class RegisterRequestDtoToCommand implements Mapper<RegisterRequestDto, RegisterCommand> {

    @Override
    public RegisterCommand convert(RegisterRequestDto dataContainer) {
        return new RegisterCommand(dataContainer.username(),
                dataContainer.password(),
                dataContainer.email(),
                dataContainer.role());
    }
}