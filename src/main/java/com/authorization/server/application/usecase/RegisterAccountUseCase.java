package com.authorization.server.application.usecase;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.authorization.server.application.port.outbound.PasswordHashingService;
import com.authorization.server.core.validation.RegisterAccountUseCaseValidator;
import com.authorization.server.core.validation.ValidationContext;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.identity.AccountLifecycleStatus;
import com.authorization.server.identity.Role;
import com.authorization.server.infrastructure.persistence.converter.Converter;
import com.authorization.server.infrastructure.persistence.jpa.contract.RoleTypeRepository;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleTypeEntity;
import com.authorization.server.infrastructure.web.payload.RegisterRequestDto;

@Service
public class RegisterAccountUseCase implements com.authorization.server.application.port.inbound.RegisterAccountUseCase {

    private static final String REGISTER_COMMAND = "registerCommand";

    private final AccountRepository accountRepository;
    private final RegisterAccountUseCaseValidator validator;
    private final PasswordHashingService passwordEncoder;
    private final RoleTypeRepository roleTypeRepository;
    private final Converter<Role, RoleTypeEntity> roleTypeMapper;

    public RegisterAccountUseCase(AccountRepository accountRepository, RegisterAccountUseCaseValidator validator,
                                  PasswordHashingService passwordEncoder, RoleTypeRepository roleTypeRepository, Converter<Role, RoleTypeEntity> roleTypeMapper) {
        this.accountRepository = accountRepository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
        this.roleTypeRepository = roleTypeRepository;
        this.roleTypeMapper = roleTypeMapper;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public Optional<Account> register(RegisterRequestDto registerRequestDto) {
        return Optional.empty();
    }


    private  Set<Role> roleTypesFromDb(Set<String> roleType) {
        Set<RoleTypeEntity> roleTypeEntities = roleType.stream()
                .map(roleTypeRepository::findByRoleTypeName).collect(Collectors.toSet());
        return roleTypeEntities.stream().map(roleTypeMapper::toDomain).collect(Collectors.toSet());
    }
}