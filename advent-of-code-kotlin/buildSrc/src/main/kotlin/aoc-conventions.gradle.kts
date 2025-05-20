/**
 * Common settings for all Advent Of Code subprojects / modules
 */
plugins {
    kotlin("jvm") // version is set trough libs.kotlin.gradle
    jacoco
}

group = "code.of.advent"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
