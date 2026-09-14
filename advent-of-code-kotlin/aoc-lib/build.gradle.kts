
plugins {
    `aoc-conventions`
}

version = "2025-SNAPSHOT"

dependencies {
    api(project(":aoc-logging"))
    testImplementation(libs.kotest.runner.junit)
    testImplementation(libs.kotest.assertions.core)
}
