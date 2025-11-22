package com.authorization.server.domain.account;

public class DomainMain {

    public static void main(String[] args) {
        var customer = Account.builder()
                .username("test")
                .password("root")
                .email("test@email.com")
                .roleType(RoleType.ROLE_ADMIN)
                .build();

        System.out.println(customer); // TODO: Write domain tests
    }
}
