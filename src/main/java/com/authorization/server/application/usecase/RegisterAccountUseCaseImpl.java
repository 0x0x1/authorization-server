package com.authorization.server.application.usecase;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.authorization.server.application.port.inbound.RegisterAccountUseCase;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.converter.dto.AccountToRegisterResponseDtoDtoConverter;
import com.authorization.server.infrastructure.persistence.converter.dto.RegisterRequestDtoToAccountDtoConverter;
import com.authorization.server.web.dto.RegisterRequestDto;
import com.authorization.server.web.dto.RegisterResponseDto;

@Service
public class RegisterAccountUseCaseImpl implements RegisterAccountUseCase {

    private final AccountRepository accountRepository;
    private final RegisterRequestDtoToAccountDtoConverter toAccount;
    private final AccountToRegisterResponseDtoDtoConverter toRegisterResponseDto;

    public RegisterAccountUseCaseImpl(AccountRepository accountRepository, RegisterRequestDtoToAccountDtoConverter toAccount, AccountToRegisterResponseDtoDtoConverter toRegisterResponseDto) {
        this.accountRepository = accountRepository;
        this.toAccount = toAccount;
        this.toRegisterResponseDto = toRegisterResponseDto;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        Account account = toAccount.convert(registerRequestDto);
        Optional<Account> savedAccount = accountRepository.save(account);

        if (savedAccount.isPresent()) {
            return toRegisterResponseDto.convert(savedAccount.get());
        }

        throw new RuntimeException("cannot register account");
    }
}