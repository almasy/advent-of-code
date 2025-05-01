/**
 * Common settings for all Advent Of Code subprojects / modules
 */
plugins {
    java
    jacoco
}

group = "code.of.advent"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.jacocoTestReport {
    // include output of all Test-derived tasks into report
    executionData(fileTree(layout.buildDirectory).include("jacoco/*.exec"))
}

tasks.named<Test>("test") {
    useJUnitPlatform {
        excludeTags = setOf("Integration")
    }
}

tasks.register<Test>("integrationTest") {
    group = "verification"
    useJUnitPlatform {
        includeTags = setOf("Integration")
    }
    shouldRunAfter("test")
}

tasks.named<DefaultTask>("check") {
    dependsOn("integrationTest")
}
