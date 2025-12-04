import org.gradle.api.file.SourceDirectorySet
import org.gradle.kotlin.dsl.support.listFilesOrdered
import java.io.File
import kotlin.text.isNotBlank
import kotlin.text.toInt

/**
 * Simple representation of AdventOfCode day name
 */
@JvmInline
value class AdventOfCodeDay(val day: String) {
    init {
        require(day.matches(DAY_REGEX)) {
            "Expected day format is `dayNN` where N is a single digit"
        }
    }

    /** Returns the number of `this` day as [Int] */
    val number get(): Int =
        day.substring(3).toInt()

    /**
     * Returns a new [AdventOfCodeDay] instance with
     * its [number] increased by `1` (compared to
     * `this.number`)
     */
    fun nextDay(): AdventOfCodeDay {
        val nextDay = number + 1
        val day = if (nextDay < 10) "day0$nextDay" else "day$nextDay"
        return AdventOfCodeDay(day)
    }

    companion object {
        private val DAY_REGEX = "day(\\d\\d)".toRegex()

        /**
         * Provides a list days for which a "daily puzzle implementation"
         * exists in the given [sources].
         */
        fun solvedDays(sources: SourceDirectorySet, sourceName: String): List<AdventOfCodeDay> =
            sources.filter { it.name == sourceName }
                .filter { it.parentFile?.name?.matches(DAY_REGEX) == true }
                .map { it.parentFile?.name ?: "" }
                .filter { it.isNotBlank() }
                .map { AdventOfCodeDay(it) }
                .toList()

        /**
         * Returns the last day for which an implementation exists in
         * the given [packageRoot] or null, in case no "daily puzzle implementation"
         * has been found.
         */
        fun lastDay(packageRoot: File): AdventOfCodeDay? =
            packageRoot
                .listFilesOrdered { it.isDirectory && it.name.matches(DAY_REGEX) }
                .lastOrNull()
                ?.let { AdventOfCodeDay(it.name) }

        /**
         * Finds a first "not yet implemented" AoC day in the given
         * [packageRoot].
         */
        fun nextDay(packageRoot: File): AdventOfCodeDay =
            lastDay(packageRoot)?.nextDay() ?: AdventOfCodeDay("day01")
    }
}
