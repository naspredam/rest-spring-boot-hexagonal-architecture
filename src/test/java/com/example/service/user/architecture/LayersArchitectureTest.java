package com.example.service.user.architecture;

import com.example.service.user.infrastructure.annotations.Adapter;
import com.example.service.user.infrastructure.annotations.Mapper;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.simpleNameEndingWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.example.service.user")
class LayersArchitectureTest {

    @ArchTest
    static final ArchRule controllers_should_have_specific_format =
            classes().that()
                    .areAnnotatedWith(RestController.class)
                    .should()
                        .bePackagePrivate()
                    .andShould()
                        .haveSimpleNameEndingWith("Controller")
                    .andShould()
                        .resideInAPackage("..adapter.api..")
                    .as("The controllers are part of the adapter api");

    @ArchTest
    static final ArchRule repositories_should_have_specific_format =
            classes().that()
                    .areAnnotatedWith(Repository.class)
                    .should()
                        .bePublic()
                    .andShould()
                        .beInterfaces()
                    .andShould()
                        .beAssignableTo(JpaRepository.class)
                    .andShould()
                        .haveSimpleNameEndingWith("Repository")
                    .andShould()
                        .resideInAPackage("..adapter.persistence..")
                    .as("Repositories are part of the adapter persistence");

    @ArchTest
    static final ArchRule adapters_should_have_specific_format =
            classes().that()
                    .areAnnotatedWith(Adapter.class)
                    .should()
                        .bePackagePrivate()
                    .andShould()
                        .haveSimpleNameEndingWith("Adapter")
                    .andShould()
                        .implement(simpleNameEndingWith("Port"))
                    .andShould()
                        .onlyAccessClassesThat()
                            .resideInAnyPackage(
                                    "..adapter..", "..application.usecase..", "..domain..",
                                    "java.lang..", "java.util..");

    @ArchTest
    static final ArchRule mappers_should_haveStaticMethods_notBeingInjected_onBeanContext =
            classes().that()
                    .areAnnotatedWith(Mapper.class)
                    .should()
                        .bePackagePrivate()
                    .andShould()
                        .haveOnlyPrivateConstructors()
                    .andShould()
                        .onlyBeAccessed()
                        .byClassesThat().areAnnotatedWith(Adapter.class)
                    .as("Mappers should be only be package scope and be called from the adapters");

    @ArchTest
    static final ArchRule domainServices_should_bePlacedOnApplication_withCorrectAnnotation =
            classes().that()
                    .areAnnotatedWith(Service.class)
                    .should()
                        .haveSimpleNameEndingWith("Service")
                    .andShould()
                        .bePackagePrivate()
                    .andShould()
                        .resideInAnyPackage("..application..")
                    .andShould()
                        .implement(simpleNameEndingWith("UseCase"))
                    .andShould()
                        .accessClassesThat().haveSimpleNameEndingWith("Port")
                    .as("Use case are our business, implemented as services, and they are based on port integration");

    @ArchTest
    static final ArchRule useCaseInterfaces_LivesOnDomain_UnderUseCasePackage =
            classes().that()
                    .haveNameMatching("\\w+UseCase")
                    .should()
                        .resideInAPackage("..application.usecase..")
                    .andShould()
                        .beInterfaces();

    @ArchTest
    static final ArchRule portInterfaces_LivesOnDomain_UnderUseCasePackage =
            classes().that()
                    .haveNameMatching("\\w+Port")
                    .should()
                        .resideInAPackage("..application.port..")
                    .andShould()
                        .beInterfaces();

}
