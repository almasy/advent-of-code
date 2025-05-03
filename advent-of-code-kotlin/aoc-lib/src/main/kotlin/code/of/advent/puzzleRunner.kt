package code.of.advent

/**
 * Simple API specifying a unified way of puzzle solution (see [Puzzle])
 * executions.
 *
 * The [puzzle] is always an implementation of [Puzzle] which solves
 * particular AoC day puzzle. The [input] is matching implementation
 * of [PuzzleInput] which provides correctly formatted data for the
 * [puzzle] solution.
 *
 * @property input provider of puzzle input data
 * @property puzzle AoC day solver
 * @property timing used for code duration measurement
 * to measure, how long a block of code runs.
 */
interface PuzzleRunner<T, R> {
    val input: PuzzleInput<T>
    val puzzle: Puzzle<T, R>
    val timing: PuzzleTiming

    /**
     * Implementation is expected to run given [puzzle] solution
     * ([Puzzle.part1] and [Puzzle.part2]) in one of the predefined
     * modes. Choice is based on given [context].
     *
     * The [input] serves as an implicit data source.
     *
     * RunModes:
     *
     * - [RunMode.DEFAULT] - runs part1 & part2 once, then provides results
     * - [RunMode.MEASURED] - runs part1 & part2 once, then provides results
     * as well as time each part took to complete.
     * - [RunMode.BENCHMARK] - performs a simple micro-benchmark by repeatedly
     * running and measuring each part of the puzzle (see also [Context.Benchmark]).
     *
     * @param context configuration affecting way [puzzle] is run
     * @return result obtained by running both [puzzle] parts as well as
     * the [input].
     */
    fun runWith(context: Context): Result<PuzzleResult<R>>
}

/**
 * [PuzzleRunner] implementation writing progress
 * reports directly to the standard output
 * (i.e. via [print]/[println]).
 *
 * Note that other than the default value of [timing] can
 * be useful only in specific cases (e.g. for test purposes).
 *
 * @property input provider of puzzle input data
 * @property puzzle puzzle solver implementation
 * @property timing see [PuzzleRunner]
 */
class PrintRunner<T, R>(
    override val input: PuzzleInput<T>,
    override val puzzle: Puzzle<T, R>,
    override val timing: PuzzleTiming = MonotonicTiming,
) : PuzzleRunner<T, R> {

    /**
     * Runs given [puzzle] solution ([Puzzle.part1] and [Puzzle.part2])
     * in one of the predefined modes (see [RunMode]). Choice of the run mode
     * is based on given [context].
     * @see PuzzleRunner.runWith
     */
    override fun runWith(context: Context): Result<PuzzleResult<R>> {
        println("Advent of Code ${puzzle.year} ${puzzle.day}")
        println("Puzzle input source: ${input.source}")
        return runCatching {
            when (context.runMode) {
                RunMode.DEFAULT -> runDefault()
                RunMode.MEASURED -> runMeasured()
                RunMode.BENCHMARK -> runBenchmark(context.benchmark)
            }
        }.onFailure { ex ->
            // TODO: log exception
            val message = when(ex::class) {
                PuzzleInputException::class -> "\nFailed to load ${puzzle.day} input!"
                else -> "\nExecution of ${puzzle.day} failed due to an unexpected error!"
            }
            println(message)
        }
    }

    /**
     * Simple "AOC day solution" runner
     */
    private fun runDefault(): PuzzleResult<R> {
        val part1 = PuzzleResult.PartResult(puzzle.part1(input.load()))
        println("Part1: ${part1.value}")
        val part2 = PuzzleResult.PartResult(puzzle.part2(input.load()))
        println("Part2: ${part2.value}")
        return PuzzleResult<R>(part1, part2)
    }

    /**
     *  Runs "AOC day solution" and measures duration of each part
     */
    private fun runMeasured(): PuzzleResult<R> {
        val (puzzleInput1, loadingDuration) = timing.measureTimedValue { input.load() }
        println("Input loaded in ${loadingDuration.toPrettyString()}")
        val part1 = timing.measureTimedValue { puzzle.part1(puzzleInput1) }
        println("Part1 result: ${part1.value} (completed in ${part1.duration.toPrettyString()})")

        // reload input as part1 may have consumed or corrupted it
        val puzzleInput2 = input.load()
        val part2 = timing.measureTimedValue { puzzle.part2(puzzleInput2) }
        println("Part2 result: ${part2.value} (completed in ${part2.duration.toPrettyString()})")

        val total = loadingDuration + part1.duration + part2.duration
        println("Total time: ${total.toPrettyString()}")

        return PuzzleResult(
            part1.toPartResult(),
            part2.toPartResult(),
            loadingDuration.toInputResult(),
            total
        )
    }

    /**
     * Performs a micro-benchmark of the puzzle solution.
     * The [benchmark] is used to set parameters of the benchmark
     * (e.g. number of loops / iterations, max. total duration)
     */
    private fun runBenchmark(benchmark: Context.Benchmark): PuzzleResult<R> {
        require(benchmark.loops > 1) { "Can't run benchmark with less than 2 iterations!" }
        println("Running benchmark with total of ${benchmark.loops} iterations")

        val benchmarkStart = timing.now()
        val deadline = benchmarkStart + benchmark.limit

        print("Measuring input loading... ")
        val inputLoad = timing.runBenchmark(input::load, benchmark.loops, deadline)
        timeoutMessage(inputLoad.loops, benchmark.loops)
        print("Done.\nMeasuring part1... ")
        val result1 = timing.runBenchmark(puzzle::part1, input::load, benchmark.loops, deadline)
        timeoutMessage(result1.loops, benchmark.loops)
        print("Done.\nMeasuring part2... ")
        val result2 = timing.runBenchmark(puzzle::part2, input::load, benchmark.loops, deadline)
        timeoutMessage(result2.loops, benchmark.loops)

        val total = timing.now() - benchmarkStart
        println("Done.\nInput loading average time: ${inputLoad.duration.toPrettyString()}")
        println("Part1 average time: ${result1.duration.toPrettyString()}")
        println("Part2 average time: ${result2.duration.toPrettyString()}")
        println("Total benchmark duration: ${total.toPrettyString()}")

        return PuzzleResult(result1, result2, inputLoad, total)
    }

    private fun timeoutMessage(actual: Int, expected: Int) {
        if (actual < expected) {
            val plural = if (actual != 1) "s" else ""
            println("Runtime exceeded maximal allowed duration!")
            print("Terminating after $actual iteration${plural}... ")
        }
    }
}
