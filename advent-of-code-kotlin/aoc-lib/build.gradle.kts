
plugins {
    `aoc-conventions`
}

version = "2025-SNAPSHOT"

kotlin {
    explicitApiWarning()
}

dependencies {
    api(project(":aoc-logging"))
    testImplementation(libs.kotest.runner.junit)
    testImplementation(libs.kotest.assertions.core)
}
