package com.example.hexagonal.arch.service.architectural;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.service.reactive.hexagonal")
class DomainArchitectureTest {

    @ArchTest
    static final ArchRule domainPackages_mustNot_haveThirdPartyLibraries =
            classes().that()
                    .resideInAnyPackage("..domain..")
                    .should()
                        .onlyHaveDependentClassesThat()
                        .resideInAnyPackage("lombok..", "java..", "javax..", "com.service.reactive.hexagonal..");

    @ArchTest
    static final ArchRule useCaseInterfaces_LivesOnDomain_UnderUseCasePackage =
            classes().that()
                        .areInterfaces()
                    .and()
                        .haveNameMatching("\\w+UseCase")
                    .should()
                        .resideInAPackage("..domain.usecase..");

    @ArchTest
    static final ArchRule portInterfaces_LivesOnDomain_UnderUseCasePackage =
            classes().that()
                    .areInterfaces()
                    .and()
                        .haveNameMatching("\\w+Port")
                    .should()
                        .resideInAPackage("..domain.port..");

}
