package com.example.service.user.architecture;

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
}
