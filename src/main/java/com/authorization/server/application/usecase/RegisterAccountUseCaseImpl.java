package com.authorization.server.application.usecase;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.authorization.server.application.command.RegisterCommand;
import com.authorization.server.application.command.RegisterCommandResult;
import com.authorization.server.application.port.inbound.RegisterAccountUseCase;
import com.authorization.server.identity.Account;
import com.authorization.server.identity.AccountRepository;
import com.authorization.server.infrastructure.persistence.converter.AccountFactory;
import com.authorization.server.infrastructure.persistence.entity.BaseEntity;
import com.authorization.server.infrastructure.persistence.entity.authorization.RoleEntity;
import com.authorization.server.infrastructure.persistence.repository.RoleRepository;

@Service
public class RegisterAccountUseCaseImpl implements RegisterAccountUseCase {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountFactory accountFactory;

    public RegisterAccountUseCaseImpl(AccountRepository accountRepository, RoleRepository roleRepository, AccountFactory accountFactory) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.accountFactory = accountFactory;
    }

    @Override
    @Transactional(REQUIRES_NEW)
    public RegisterCommandResult handle(RegisterCommand registerCommand) {

        Collection<UUID> roleIds = registerCommand.roles().stream()
                .map(roleRepository::findByDisplayName)
                .map(BaseEntity::getId)
                .toList();

        Account account = accountFactory.from(registerCommand, roleIds);

        Optional<Account> savedAccount = accountRepository.save(account);

        Collection<RoleEntity> roleEntities =
                savedAccount.get().getRoleIds().stream()
                        .map(roleRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList();

        if (savedAccount.isPresent()) {
            return accountFactory.toRegisterCommandResult(savedAccount.get(), roleEntities);
        } else {
            throw new IllegalStateException("Account could not be savedAccount");
        }
    }
}