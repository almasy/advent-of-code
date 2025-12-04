import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.VerificationException
import org.gradle.api.tasks.options.Option
import templates.solutionTemplate
import java.io.IOException
import kotlin.text.replace

/**
 * Gradle task adding a scaffolding for an Advent of Code
 * puzzle implementation.
 */
abstract class  AdventOfCodeDayTask : DefaultTask() {

    /** Root (AoC project) directory used as basis for all relative paths */
    @get:InputDirectory
    abstract val root: DirectoryProperty

    /** A Java/Kotlin package for AoC day implementation */
    @get:Input
    abstract val classPackage: Property<String>

    /** AoC day implementation Kotlin class / file name */
    @get:Input
    abstract val fileName: Property<String>

    /** AoC year */
    @get:Input
    abstract val year: Property<String>

    /** AoC day */
    @get:Input
    @get:Optional
    @get:Option("day", "Specific AoC day")
    abstract val day: Property<String>

    @TaskAction
    fun addNextDay() {
        val packagePath = classPackage.get().replace('.', '/')
        val basePath = root.get().dir("src/main/kotlin/$packagePath").asFile
        val nextDay = dayToAdventOfCodeDayOrNull() ?: AdventOfCodeDay.nextDay(basePath)

        val classPath = "src/main/kotlin/$packagePath/${nextDay.day}"
        val directory = root.get().dir(classPath).asFile
        if (!directory.mkdirs() && !directory.exists()) {
            throw VerificationException(
                "Failed to create directory for AoC Day ${nextDay.number} package")
        }

        try {
            val classFile = root.get().file("${classPath}/${fileName.get()}").asFile
            if (classFile.exists()) {
                throw VerificationException(
                    "Solution sources for AoC Day ${nextDay.number} already exist")
            }
            classFile.writeText(
                solutionTemplate(
                    classPackage = "${classPackage.get()}.${nextDay.day}",
                    year = year.get(),
                    day = nextDay.day.replaceFirstChar(Char::uppercaseChar),
                    dayNumber = nextDay.number,
                ))
            logger.info("Created scaffolding for AoC Day {}", nextDay.number)
        } catch (e: IOException) {
            throw VerificationException(
                "Failed to create scaffolding for AoC Day ${nextDay.number}", e)
        }
    }

    fun dayToAdventOfCodeDayOrNull(): AdventOfCodeDay? =
        try {
            day.orNull?.let { AdventOfCodeDay(it) }
        } catch (_: IllegalArgumentException) {
            null
        }

}
