package code.of.advent

import java.util.Properties
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

const val DEFAULT_CONFIG_FILE = "./src/test/resources/config.properties"
val DEFAULT_RUN_MODE = RunMode.DEFAULT
val DEFAULT_DURATION = 1.minutes
const val DEFAULT_INPUT_ROOT = "./src/main/resources/inputs"
val DEFAULT_INPUT_SESSION: String? = null
const val DEFAULT_LOOPS = 100
const val DEFAULT_WARMUP = 1

const val CUSTOM_LOOPS = 10
const val HIGH_LOOPS = 100
val CUSTOM_DURATION = 1.minutes

object DefaultRunContext : Context {
    override val input = Context.Input(DEFAULT_INPUT_ROOT, DEFAULT_INPUT_SESSION)
    override val runMode = RunMode.DEFAULT
    override val benchmark = Context.Benchmark(CUSTOM_LOOPS, DEFAULT_WARMUP,CUSTOM_DURATION)
}

object MeasureRunContext : Context {
    override val input = Context.Input(DEFAULT_INPUT_ROOT, DEFAULT_INPUT_SESSION)
    override val runMode = RunMode.MEASURED
    override val benchmark = Context.Benchmark(CUSTOM_LOOPS, DEFAULT_WARMUP, CUSTOM_DURATION)
}

object BenchmarkRunContext : Context {
    override val input = Context.Input(DEFAULT_INPUT_ROOT, DEFAULT_INPUT_SESSION)
    override val runMode = RunMode.BENCHMARK
    override val benchmark = Context.Benchmark(CUSTOM_LOOPS, DEFAULT_WARMUP, CUSTOM_DURATION)
}

object HighLoopsContext : Context {
    override val input = Context.Input(DEFAULT_INPUT_ROOT, DEFAULT_INPUT_SESSION)
    override val runMode = RunMode.BENCHMARK
    override val benchmark = Context.Benchmark(HIGH_LOOPS, DEFAULT_WARMUP, CUSTOM_DURATION)
}

object InvalidContext : Context {
    override val input = Context.Input(DEFAULT_INPUT_ROOT, DEFAULT_INPUT_SESSION)
    override val runMode = RunMode.BENCHMARK
    override val benchmark = Context.Benchmark(0, -1, (-1).seconds)
}

fun envMapOf(
    inputRoot: String,
    inputSession: String,
    runMode: String,
    warmUp: String,
    loops: String,
    limit: String) = mapOf(
        "AOC_PUZZLE_INPUT_ROOT" to inputRoot,
        "AOC_PUZZLE_INPUT_SESSION" to inputSession,
        "AOC_PUZZLE_RUN_MODE" to runMode,
        "AOC_BENCHMARK_WARMUP" to warmUp,
        "AOC_BENCHMARK_ITERATIONS" to loops,
        "AOC_BENCHMARK_LIMIT_MINUTES" to limit
    )

fun propertiesOf(
    inputRoot: String,
    inputSession: String,
    runMode: String,
    warmUp: String,
    loops: String,
    limit: String) = Properties().apply {
        put("puzzle.input.root", inputRoot)
        put("puzzle.input.session", inputSession)
        put("puzzle.run.mode", runMode)
        put("benchmark.warmup", warmUp)
        put("benchmark.iterations", loops)
        put("benchmark.limit.minutes", limit)
    }
