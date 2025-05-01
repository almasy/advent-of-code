
plugins {
    `aoc-conventions`
}

version = "2025-SNAPSHOT"

dependencies {
    testImplementation(libs.kotest.runner.junit)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.framework.datatest)
}
