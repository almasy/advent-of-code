
plugins {
    `aoc-conventions`
    `advent-of-code`
}

version = "2024-SNAPSHOT"

dependencies {
    implementation(project(":aoc-lib"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.assertj.core)
    testRuntimeOnly(libs.junit.platform.launcher)
}
