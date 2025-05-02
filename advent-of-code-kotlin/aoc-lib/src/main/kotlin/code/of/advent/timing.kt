package code.of.advent

import kotlin.time.Duration
import kotlin.time.TimeSource
import kotlin.time.TimedValue

/** The full name [TimeSource.Monotonic.ValueTimeMark] is just too long... */
typealias TimeMark = TimeSource.Monotonic.ValueTimeMark

/**
 * Abstraction allowing to "shield" from
 * Kotlin's default time / duration measurement,
 * shall that be needed.
 */
interface Timing {
    /**
     * Implementation should measure a duration of given
     * code block and then return it together with the result
     * of the called block, all wrapped in [TimedValue].
     * @param block block of code to be measured
     */
    fun <T> measureTimedValue(block: () -> T): TimedValue<T>

    /**
     * Implementation should measure a duration of given
     * code block and return as [Duration]
     * @param block block of code to be measured
     */
    fun measureTime(block: () -> Unit): Duration

    /**
     * Implementation should provide a [TimeMark] value
     * denoting "timestamp" of the current moment.
     */
    fun now(): TimeMark
}

interface PuzzleTiming: Timing {
    /**
     * Simple benchmarking for any [PuzzleInput.load]
     * implementation.
     * @param puzzleInput reference to [PuzzleInput.load]
     *                    implementation
     * @param loops number of measurement iterations
     * @param deadline time at which benchmark will
     *                 stop without completing all
     *                 iterations.
     * @return result of benchmark stored in [PuzzleResult.InputResult]
     */
    fun <T> runBenchmark(
        puzzleInput: () -> T,
        loops: Int,
        deadline: TimeMark
    ): PuzzleResult.InputResult {
        var duration = Duration.ZERO
        var i = 0
        do {
            duration += measureTime { puzzleInput }
        } while (++i < loops && now() < deadline)

        return (duration / i).toInputResult(i)
    }

    /**
     * Simple benchmarking for any [Puzzle.part1]
     * and [Puzzle.part2] implementation.
     * Works on a major assumption, that [PuzzleInput.load]
     * is implemented as well.
     * @param puzzlePart a [Puzzle] part under benchmark
     * @param puzzleInput reference to matching [PuzzleInput.load]
     * @param loops number of measurement iterations
     * @param deadline time at which will the benchmark
     *                 stop without completing all iterations.
     * @return result of benchmark stored in [PuzzleResult.PartResult]
     */
    fun <T, R> runBenchmark(
        puzzlePart: (T) -> R,
        puzzleInput: () -> T,
        loops: Int,
        deadline: TimeMark
    ): PuzzleResult.PartResult<R> {
        var timedValue: TimedValue<R>
        var totalDuration = Duration.ZERO
        var i = 0
        do {
            val puzzleData = puzzleInput()
            timedValue = measureTimedValue { puzzlePart(puzzleData) }
            totalDuration += timedValue.duration
        } while (++i < loops && deadline > now())

        return PuzzleResult.PartResult(timedValue.value, totalDuration / i, i)
    }
}

/**
 * Default Timing implementation. Use this
 * one unless you want to achieve something
 * very particular.
 */
object MonotonicTiming: PuzzleTiming {
    /**
     * Uses default Kotlin's way of measuring duration
     * of a code block execution
     * @param block block of code to be measured
     */
    override fun <T> measureTimedValue(block: () -> T): TimedValue<T> =
        kotlin.time.measureTimedValue(block)

    /**
     * Uses default Kotlin's way of measuring duration
     * of a code block execution
     *@param block block of code to be measured
     */
    override fun measureTime(block: () -> Unit): Duration =
        kotlin.time.measureTime(block)

    /**
     * Uses default Kotlin's way of creating a "timestamp"
     * which can be used to determine time differences
     * between "stamped" intervals.
     */
    override fun now(): TimeMark = TimeSource.Monotonic.markNow()
}
