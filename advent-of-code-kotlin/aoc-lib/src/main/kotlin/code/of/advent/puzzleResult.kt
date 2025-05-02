package code.of.advent

import kotlin.time.Duration
import kotlin.time.TimedValue

/**
 * Data structure holding [PuzzleRunner] result.
 * @property part1 result of day's part1 solution
 * @property part2 result of day's part2 solution
 * @property input duration of puzzle input loading (if measured)
 * @property total total duration (if measured)
 * @see Puzzle
 * @see PuzzleRunner
 */
data class PuzzleResult<T>(
    val part1: PartResult<T>,
    val part2: PartResult<T>,
    val input: InputResult = InputResult(),
    val total: Duration = Duration.ZERO) {

    /**
     * Data structure for keeping [Puzzle] part results.
     * @property value actual calculated result value
     * @property duration duration of calculation (if measured)
     * @property loops number of iterations (solution calls) performed
     * @property <T> type of the day's solution result
     * @see Puzzle
     */
    data class PartResult<T>(
        val value: T,
        val duration: Duration = Duration.ZERO,
        val loops: Int = 1)

    /**
     * Data structure for keeping [PuzzleInput]
     * measurement results.
     * @property duration duration of calculation (if measured)
     * @property loops number of iterations (input load calls) performed
     */
    data class InputResult(
        val duration: Duration = Duration.ZERO,
        val loops: Int = 1)
}

/**
 * Convenience conversion function
 */
fun <T> TimedValue<T>.toPartResult(loops: Int = 1): PuzzleResult.PartResult<T> =
    PuzzleResult.PartResult(this.value, this.duration, loops)

/**
 * Convenience conversion function
 */
fun Duration.toInputResult(loops: Int = 1): PuzzleResult.InputResult =
    PuzzleResult.InputResult(this, loops)
