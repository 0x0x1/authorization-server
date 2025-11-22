package com.authorization.server.infrastructure.persistence.jpa.entity.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER_ACCOUNT")
@Getter
@Setter
public class CustomerAccountEntity extends AccountEntity {
}
