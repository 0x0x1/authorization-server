package com.authorization.server.application.usecase;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.authorization.server.application.port.inbound.RegisterAccountUseCase;
import com.authorization.server.common.validation.RegisterAccountUseCaseValidator;
import com.authorization.server.common.validation.ValidationContext;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.jpa.converter.dto.AccountToRegisterResponseDtoConverter;
import com.authorization.server.infrastructure.persistence.jpa.converter.dto.RegisterRequestDtoToAccountConverter;
import com.authorization.server.web.dto.RegisterRequestDto;
import com.authorization.server.web.dto.RegisterResponseDto;

@Service
public class RegisterAccountUseCaseImpl implements RegisterAccountUseCase {

    private final AccountRepository accountRepository;
    private final RegisterRequestDtoToAccountConverter toAccount;
    private final AccountToRegisterResponseDtoConverter toRegisterResponseDto;
    private final RegisterAccountUseCaseValidator validator;

    public RegisterAccountUseCaseImpl(AccountRepository accountRepository, RegisterRequestDtoToAccountConverter toAccount, AccountToRegisterResponseDtoConverter toRegisterResponseDto, RegisterAccountUseCaseValidator validator) {
        this.accountRepository = accountRepository;
        this.toAccount = toAccount;
        this.toRegisterResponseDto = toRegisterResponseDto;
        this.validator = validator;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        ValidationContext ctx = new ValidationContext("register-new-account-context");
        validator.validateAndThrow(registerRequestDto, ctx);

        Account account = toAccount.convert(registerRequestDto);
        Optional<Account> savedAccount = accountRepository.save(account);

        if (savedAccount.isPresent()) {
            return toRegisterResponseDto.convert(savedAccount.get());
        }

        throw new RuntimeException("cannot register account");
    }

}