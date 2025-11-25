package code.of.advent

import java.io.File
import java.io.IOException
import kotlin.io.path.Path
import kotlin.io.path.absolute

/**
 * Exception expected to be thrown, if a problem
 * occurs during the puzzle loading and parsing
 * (e.g. given source doesn't exist, puzzle
 * format is invalid, etc.).
 */
class PuzzleInputException(
    message: String,
    source: Throwable? = null
): Exception(message, source)

/**
 * Generic API specification for a puzzle input providers.
 * @property source full path to puzzle data file for given AoC day.
 * @property day name of the AoC day class implementing
 * [PuzzleInput] belongs to.
 */
interface PuzzleInput<T> {
    /**
     * Default source of "raw" puzzle data. Make
     * sure to set this value, in order to adjust,
     * from where the [load] method reads the puzzle
     * data.
     */
    val source: String

    /**
     * Extracts the "day number" (e.g. "day07") from the
     * name of the class. This means the class must conform
     * to the naming convention, or must override this value.
     */
     val day: String
        get() = dayFromClass()

    /**
     * Implementation must load puzzle input
     * from given ([fileName]) and convert
     * it to a format [T] expected by the puzzle
     * solution.
     * @throws PuzzleInputException
     */
    fun loadFrom(fileName: String): T

    /**
     * Implementation, if provided, must load puzzle
     * input from the default [source] and convert
     * it to a format [T] expected by the puzzle
     * solution.
     * @throws PuzzleInputException
     */
    fun load(): T = loadFrom(source)

    /**
     * Helper composing full path to a puzzle source file
     * from context and class name.
     *
     * Use this method to obtain a default / common puzzle
     * file path.
     *
     * Override [day] to generate custom source file name
     */
    fun buildSourceWith(context: Context): String =
        Path(context.input.root, "${day}.txt").absolute().normalize().toString()

    private fun dayFromClass(): String =
        this::class.simpleName?.let { name ->
            "Day\\d+".toRegex().find(name)?.value?.lowercase()
        } ?: throw IllegalStateException("Can't parse day from: ${this::class.simpleName}")
}

/**
 * Helper calling the [block] providing it a new [File] instance
 * created with [fileName] passed as parameter, and converting
 * "well known" exceptions to [PuzzleInputException].
 *
 * Current list of "well known" exceptions:
 * - [IOException]
 * - [NumberFormatException]
 * - [IndexOutOfBoundsException]
 * - [IllegalArgumentException]
 * @throws PuzzleInputException
 */
inline fun <T> runWithFile(fileName: String, block: (File) -> T): T =
    try {
        block(File(fileName))
    } catch (e: IOException) {
        throw PuzzleInputException("Failed to load puzzle from $fileName", e)
    } catch (e: NumberFormatException) {
        throw PuzzleInputException("Puzzle in $fileName has invalid format", e)
    } catch (e: IndexOutOfBoundsException) {
        throw PuzzleInputException("Puzzle in $fileName has invalid format", e)
    } catch (e: IllegalArgumentException) {
        throw PuzzleInputException("Puzzle in $fileName has invalid format", e)
    }
