
plugins {
    `aoc-conventions`
}

version = "2025-SNAPSHOT"

dependencies {
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.assertj.core)
    testRuntimeOnly(libs.junit.platform.launcher)
    testFixturesApi(libs.junit.jupiter)
}
