import org.gradle.api.provider.Property

/**
 * Extension of `advent-of-code` plugin
 * @property year an Advent of Code year
 * @property rootPackage parent of all day packages
 * @property className class with main function
 * @property sourceName source file name with main function
 * @property taskGroup task group name for all day runner tasks
 */
interface AdventOfCodePluginExtension {
    /** AoC year. Default value is extracted from project name */
    val year: Property<String>
    /** Common package holding all day packages */
    val rootPackage: Property<String>
    /** Simple class name of the class with the main function */
    val className: Property<String>
    /** File name of the source holding class with the main function */
    val sourceName: Property<String>
    /** Name of the gradle task group for day runner tasks */
    val taskGroup: Property<String>

    /**
     * Provides a full class name consisting of rootPackage, day & className
     */
    fun fullClassName(day: String): String = "${rootPackage.get()}.${day}.${className.get()}"

    /**
     * Sets defaults (a.k.a. conventions)
     */
    fun setConventions(projectName: String) {
        // leverage naming by convention - try to get year from the project name
        AdventOfCodeDefaults.YEAR_REGEX.find(projectName)?.let {
            year.convention(it.groupValues[1])
        }
        if (AdventOfCodeDefaults.YEAR_REGEX.matches(projectName))
            taskGroup.convention(projectName)
        else
            taskGroup.convention("aoc-${year.get()}")

        rootPackage.convention(AdventOfCodeDefaults.ROOT_PACKAGE)
        className.convention(AdventOfCodeDefaults.CLASS_NAME)
        sourceName.convention(AdventOfCodeDefaults.SOURCE_NAME)
    }
}

private object AdventOfCodeDefaults {
    const val ROOT_PACKAGE = "code.of.advent"
    const val CLASS_NAME = "SolutionKt"
    const val SOURCE_NAME = "solution.kt"
    val YEAR_REGEX = "aoc-(\\d{4})".toRegex()
}
