/**
 * Common settings for all Advent Of Code Kotlin subprojects / modules
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
    jvmToolchain(25)
}

jacoco {
    toolVersion = "0.8.15"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
