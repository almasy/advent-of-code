import org.gradle.api.tasks.JavaExec

/**
 * Custom task type for AdventOfCode day runner tasks
 */
abstract class AdventOfCodeTask: JavaExec() {
    private val customProperties = listOf(
        "puzzle.input.root",
        "puzzle.input.session",
        "puzzle.run.mode",
        "benchmark.iterations",
        "benchmark.warmup",
        "benchmark.limit.minutes"
    )

    init {
        // project properties (added via -P) have got higher priority
        customProperties
            .filter { project.hasProperty(it) }
            .forEach { systemProperty(it, project.property(it)!!) }
        // check system properties (added via -D) for not yet populated values
        customProperties
            .filter { !project.hasProperty(it) }
            .filter { System.getProperty(it) != null }
            .forEach { systemProperty(it, System.getProperty(it)) }
    }
}
