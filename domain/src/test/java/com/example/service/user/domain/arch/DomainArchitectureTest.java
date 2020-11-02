package com.example.service.user.domain.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.example.service.user")
class DomainArchitectureTest {

    @ArchTest
    static final ArchRule domainPackages_mustNot_haveThirdPartyLibraries =
            classes().that()
                    .resideInAnyPackage("..domain..")
                    .should()
                        .onlyHaveDependentClassesThat()
                        .resideInAnyPackage(
                                "lombok..", "java..", "javax..",
                                "com.example.service.user..", "com.example.service.common..");

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
