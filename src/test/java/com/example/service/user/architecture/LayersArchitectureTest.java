package com.example.service.user.architecture;

import com.example.service.user.infrastructure.annotations.Adapter;
import com.example.service.user.infrastructure.annotations.Mapper;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.simpleNameContaining;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.simpleNameEndingWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.example.service.user")
class LayersArchitectureTest {

    @ArchTest
    static final ArchRule controllers_areTaggedAndLocatedOnAdapterApi =
            classes().that()
                    .areAnnotatedWith(RestController.class)
                    .should()
                        .bePackagePrivate()
                    .andShould()
                        .haveSimpleNameEndingWith("Controller")
                    .andShould()
                        .resideInAPackage("..adapter.entrypoint.api..");

    @ArchTest
    static final ArchRule repositories_shouldBeLocatedOnAdapterPersistence_AndFollowingStructure =
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
                        .resideInAPackage("..adapter.persistence..");

    @ArchTest
    static final ArchRule adapters_shouldBeOnItsPackage_AndExtendingPort =
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
                                    "..adapter..", "..application.usecase..", "..domain..", "..infrastructure..",
                                    "java.lang..", "java.util..", "reactor.core..");

    @ArchTest
    static final ArchRule mappers_should_bePackageScope_AndOnlyBeAccessedByAdapters =
            classes().that()
                    .areAnnotatedWith(Mapper.class)
                    .should()
                        .bePackagePrivate()
                    .andShould()
                        .onlyBeAccessed()
                        .byClassesThat(simpleNameContaining("Adapter")
                                .or(simpleNameContaining("Mapper")));

    @ArchTest
    static final ArchRule services_shouldBeOnApplicationPackage_ImplementUseCase_AndCallPorts =
            classes().that()
                    .areAnnotatedWith(Service.class)
                    .should()
                        .haveSimpleNameEndingWith("Service")
                    .andShould()
                        .bePackagePrivate()
                    .andShould()
                        .resideInAnyPackage("..application.service..")
                    .andShould()
                        .implement(simpleNameEndingWith("UseCase"))
                    .andShould()
                        .accessClassesThat().haveSimpleNameEndingWith("Port");

    @ArchTest
    static final ArchRule useCases_shouldBeInProperPackage_AndBeInterfaces =
            classes().that()
                    .haveNameMatching("\\w+UseCase")
                    .should()
                        .resideInAPackage("..application.usecase..")
                    .andShould()
                        .beInterfaces();

    @ArchTest
    static final ArchRule ports_shouldBeInProperPackage_AndBeInterfaces =
            classes().that()
                    .haveNameMatching("\\w+Port")
                    .should()
                        .resideInAPackage("..application.port..")
                    .andShould()
                        .beInterfaces();

}
