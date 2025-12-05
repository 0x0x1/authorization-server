package com.authorization.server.infrastructure.persistence.converter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.authorization.server.identity.Account;
import com.authorization.server.identity.Credentials;
import com.authorization.server.identity.EmailAddress;
import com.authorization.server.infrastructure.persistence.jpa.contract.RoleRepository;
import com.authorization.server.infrastructure.persistence.jpa.entity.authorization.RoleEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.AccountEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.CredentialsEntity;
import com.authorization.server.infrastructure.persistence.jpa.entity.identity.EmailAddressEntity;

/**
 * Converts between the domain {@link Account} value object and its
 * persistence-side representation {@link AccountEntity}.
 *
 * <p>This class belongs to the infrastructure layer, and it ensures that the
 * domain does not depend on JPA annotations or database-specific constraints.</p>
 *
 * <p>All invariant checks remain in the domain object. This converter is
 * responsible only for mapping values, not validating business rules.</p>
 */
@Component
public class AccountToAccountEntityConverter implements Converter<Account, AccountEntity> {

    private final RoleRepository roleRepository;
    private final CredentialsConverter credentialsConverter;
    private final EmailAddressConverter emailAddressConverter;

    /**
     * Creates a new {@code AccountToAccountEntityConverter} with the required
     * infrastructure components used to translate domain {@code Account} aggregates
     * into their persistence {@code AccountEntity} counterparts.
     *
     * @param roleRepository        the repository used to resolve and attach
     *                              persisted {@code RoleEntity} instances when converting;
     *                              must not be {@code null}
     * @param credentialsConverter  the converter responsible for translating
     *                              {@code Credentials} value objects to their persistence
     *                              representation; must not be {@code null}
     * @param emailAddressConverter the converter responsible for translating
     *                              {@code EmailAddress} value objects to their persistence
     *                              representation; must not be {@code null}
     */
    public AccountToAccountEntityConverter(RoleRepository roleRepository, CredentialsConverter credentialsConverter, EmailAddressConverter emailAddressConverter) {
        this.roleRepository = roleRepository;
        this.credentialsConverter = credentialsConverter;
        this.emailAddressConverter = emailAddressConverter;
    }

    /**
     * Converts a domain {@link Account} value object into its JPA
     * persistence representation {@link AccountEntity}.
     *
     * <p>A {@code null} input is not allowed. Null-checking is performed at the
     * boundary since conversion happens in the infrastructure layer, outside of
     * domain control.</p>
     *
     * @param fromSource the domain Account aggregate root object
     * @return a new {@link AccountEntity} containing the domain representation
     * @throws NullPointerException if {@code fromSource} is null
     */
    @Override
    public AccountEntity toEntity(Account fromSource) {
        Objects.requireNonNull(fromSource);

        CredentialsEntity credentialsEntity = credentialsConverter.toEntity(fromSource.getCredentials());
        EmailAddressEntity emailAddressEntity = emailAddressConverter.toEntity(fromSource.getEmailAddress());

        List<RoleEntity> roleEntities = fromSource.getRoleIds().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get).toList();

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCredentials(credentialsEntity);
        accountEntity.setEmail(emailAddressEntity);
        accountEntity.setRoleEntities(roleEntities);
        accountEntity.setLifecycleStatus(fromSource.getAccountLifecycleStatus());
        accountEntity.setLockStatus(fromSource.getAccountLockStatus());
        accountEntity.setLastStatusChangeAt(fromSource.getLastStatusChangedAt());

        return accountEntity;
    }

    /**
     * Converts a persistence {@link AccountEntity} into the domain value object
     * {@link Account}. Domain constructor invariants are enforced inside the
     * {@code Username} class itself.
     *
     * <p>A {@code null} input is not allowed. Null-checking here protects the
     * domain from receiving invalid or incomplete data from persistence.</p>
     *
     * @param fromTarget the persistence-side Account entity object
     * @return a domain {@link Account} instance created from the stored object
     * @throws NullPointerException if {@code fromTarget} is null
     */
    @Override
    public Account toDomain(AccountEntity fromTarget) {
        Objects.requireNonNull(fromTarget);

        Credentials credentials = credentialsConverter.toDomain(fromTarget.getCredentials());
        EmailAddress emailAddress = emailAddressConverter.toDomain(fromTarget.getEmail());
        List<UUID> rolesId = fromTarget.getRoleEntities().stream().map(RoleEntity::getId).toList();

        return Account.builder()
                .credentials(credentials)
                .emailAddress(emailAddress)
                .roleIds(rolesId)
                .build();

    }
}