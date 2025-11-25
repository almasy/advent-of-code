
plugins {
    `aoc-conventions`
    `advent-of-code`
}

version = "2024-SNAPSHOT"

dependencies {
    api(project(":aoc-lib"))
    implementation(libs.kotlinx.coroutines)
    testImplementation(libs.kotest.runner.junit)
    testImplementation(libs.kotest.assertions.core)
}
