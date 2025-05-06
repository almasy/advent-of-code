import org.gradle.api.file.SourceDirectorySet
import kotlin.text.isNotBlank

/**
 * Simple representation of AdventOfCode day name
 */
@JvmInline
value class AdventOfCodeDay(val day: String) {
    fun toNumber(): String =
        "${DAY_REGEX.find(day)?.groupValues?.get(1)?.toInt()}"

    companion object {
        private val DAY_REGEX = "day(\\d\\d)".toRegex()

        /**
         * Provides a list of all source files which match the "daily puzzle implementation"
         * criteria.
         */
        fun solvedDays(sources: SourceDirectorySet, sourceName: String): List<AdventOfCodeDay> =
            sources.filter { it.name == sourceName }
                .filter { it.parentFile?.name?.matches(DAY_REGEX) == true }
                .map { it.parentFile?.name ?: "" }
                .filter { it.isNotBlank() }
                .map { AdventOfCodeDay(it) }
                .toList()
    }
}
