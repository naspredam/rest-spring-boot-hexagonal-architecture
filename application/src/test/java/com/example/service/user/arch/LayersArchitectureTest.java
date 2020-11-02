package com.example.service.user.arch;

import com.example.service.common.annotation.Adapter;
import com.example.service.common.annotation.Mapper;
import com.example.service.common.annotation.Repository;
import com.example.service.common.annotation.UseCase;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.data.jpa.repository.JpaRepository;
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
                        .resideInAPackage("..adapter.api..");

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
                        .resideInAPackage("..adapter.persistence..");

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
                        .onlyBeAccessed()
                        .byAnyPackage("..adapter..", "..usecase..");

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
                        .byClassesThat().areAnnotatedWith(Adapter.class);

    @ArchTest
    static final ArchRule domainServices_should_bePlacedOnApplication_withCorrectAnnotation =
            classes().that()
                    .areAnnotatedWith(UseCase.class)
                    .should()
                        .haveSimpleNameEndingWith("Service")
                    .andShould()
                        .bePackagePrivate()
                    .andShould()
                        .resideInAnyPackage("..application..")
                    .andShould()
                        .implement(simpleNameEndingWith("UseCase"))
                    .andShould()
                        .accessClassesThat().haveSimpleNameEndingWith("Port");

}
