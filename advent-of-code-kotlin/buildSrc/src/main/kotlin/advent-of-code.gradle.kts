/**
 * Dedicated Advent of Code gradle plugin
 * Creates a "run given day" task for each (auto-detected) implemented
 * day plus a runAll task which can be used to trigger execution
 * of all implemented days in a sequence.
 */

plugins {
    kotlin("jvm")
}

private val config = project.extensions.create<AdventOfCodePluginExtension>("advent-of-code")
config.setConventions(projectName = project.name)
// the solvedDays must be run after setConventions as it relies on sourceName provided by the extension
private val aocDays = AdventOfCodeDay.solvedDays(sourceSets["main"].kotlin, config.sourceName.get())

// Register & configure task for each of the found days
aocDays.forEach { aocDay ->
    tasks.register<AdventOfCodeTask>(aocDay.day) {
        description = "Runs Day ${aocDay.toNumber()} of the Advent of Code ${config.year.get()}"
        group = config.taskGroup.get()
        classpath = sourceSets["main"].runtimeClasspath
        mainClass = config.fullClassName(aocDay.day)
        doLast { println() }
    }
}

if (aocDays.isNotEmpty()) {
    tasks.register<Task>("runAll") {
        description = "Runs all ${aocDays.size} days of the Advent of Code ${config.year.get()}"
        group = config.taskGroup.get()
        doLast { println("Completed ${aocDays.size} days.") }
        dependsOn(
            tasks.withType<AdventOfCodeTask>().matching {
                it.name.startsWith("day")
            }.sortedBy { it.name }
        )
    }
}
