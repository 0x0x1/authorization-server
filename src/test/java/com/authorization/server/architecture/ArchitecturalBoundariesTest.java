package com.authorization.server.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.importer.ClassFileImporter;

public class ArchitecturalBoundariesTest {

    private static final String BASE_PACKAGE = "com.authorization.server";
    private static final String APPLICATION_PACKAGE = BASE_PACKAGE + "..application..";
    private static final String DOMAIN_PACKAGE = BASE_PACKAGE + "..domain..";
    private static final String INFRASTRUCTURE_PACKAGE = BASE_PACKAGE + "..infrastructure..";

    @Test
    void application_must_not_access_infrastructure() {
        noClasses()
                .that().resideInAPackage(APPLICATION_PACKAGE)
                .should().accessClassesThat()
                .resideInAPackage(INFRASTRUCTURE_PACKAGE)
                .check(new ClassFileImporter().importPackages(BASE_PACKAGE));
    }

    @Test
    void domain_must_not_access_application() {
        noClasses()
                .that().resideInAPackage(DOMAIN_PACKAGE)
                .should().accessClassesThat()
                .resideInAPackage(APPLICATION_PACKAGE)
                .check(new ClassFileImporter().importPackages(BASE_PACKAGE));
    }

    @Test
    void domain_must_not_access_infrastructure() {
        noClasses()
                .that().resideInAPackage(DOMAIN_PACKAGE)
                .should().accessClassesThat()
                .resideInAPackage(INFRASTRUCTURE_PACKAGE)
                .check(new ClassFileImporter().importPackages(BASE_PACKAGE));
    }

    @Test
    void domain_should_not_depend_on_frameworks() {
        noClasses()
                .that().resideInAPackage(DOMAIN_PACKAGE)
                .should().accessClassesThat()
                .resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence..",
                        "javax.persistence..",
                        "org.hibernate.."
                )
                .check(new ClassFileImporter().importPackages(BASE_PACKAGE));
    }
}