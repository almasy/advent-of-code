package code.of.advent

import java.util.*
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

const val DEFAULT_CONFIG_FILE = "./src/test/resources/config.properties"
val DEFAULT_RUN_MODE = RunMode.DEFAULT
val DEFAULT_DURATION = 1.minutes
const val DEFAULT_INPUT_ROOT = "./src/main/resources/inputs"
const val DEFAULT_LOOPS = 100

const val CUSTOM_LOOPS = 10
const val HIGH_LOOPS = 100
val CUSTOM_DURATION = 1.minutes

object DefaultRunContext : Context {
    override val benchmark = Context.Benchmark(CUSTOM_LOOPS, CUSTOM_DURATION)
    override val inputRoot = DEFAULT_INPUT_ROOT
    override val runMode = RunMode.DEFAULT
}

object MeasureRunContext : Context {
    override val benchmark = Context.Benchmark(CUSTOM_LOOPS, CUSTOM_DURATION)
    override val inputRoot = DEFAULT_INPUT_ROOT
    override val runMode = RunMode.MEASURED
}

object BenchmarkRunContext : Context {
    override val benchmark = Context.Benchmark(CUSTOM_LOOPS, CUSTOM_DURATION)
    override val inputRoot = DEFAULT_INPUT_ROOT
    override val runMode = RunMode.BENCHMARK
}

object HighLoopsContext : Context {
    override val benchmark = Context.Benchmark(HIGH_LOOPS, CUSTOM_DURATION)
    override val inputRoot = DEFAULT_INPUT_ROOT
    override val runMode = RunMode.BENCHMARK
}

object InvalidContext : Context {
    override val benchmark = Context.Benchmark(0, (-1).seconds)
    override val inputRoot = DEFAULT_INPUT_ROOT
    override val runMode = RunMode.BENCHMARK
}

fun envMapOf(inputRoot: String, runMode: String, loops: String, limit: String) =
    mapOf<String, String>(
        "AOC_PUZZLE_INPUT_ROOT" to inputRoot,
        "AOC_PUZZLE_RUN_MODE" to runMode,
        "AOC_BENCHMARK_ITERATIONS" to loops,
        "AOC_BENCHMARK_LIMIT_MINUTES" to limit
    )

fun propertiesOf(inputRoot: String, runMode: String, loops: String, limit: String) =
    Properties().apply {
        put("puzzle.input.root", inputRoot)
        put("puzzle.run.mode", runMode)
        put("benchmark.iterations", loops)
        put("benchmark.limit.minutes", limit)
    }
