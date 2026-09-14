
plugins {
    `aoc-conventions`
}

version = "2026-SNAPSHOT"

kotlin {
    explicitApiWarning()
}

dependencies {
    testImplementation(libs.kotest.runner.junit)
    testImplementation(libs.kotest.assertions.core)
}
