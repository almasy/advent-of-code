package code.of.advent

import java.util.Properties
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

/**
 * - [DEFAULT] run [Puzzle.part1] & [Puzzle.part2] and provide results.
 * - [MEASURED] measure duration of input loading ([PuzzleInput.load]),
 * [Puzzle.part1] and [Puzzle.part2], and then provide both results
 * and durations.
 * - [BENCHMARK] run a micro-benchmark of input loading ([PuzzleInput.load]),
 * [Puzzle.part1] and [Puzzle.part2], and then provide results and durations.
 *
 * @see [PuzzleRunner.runWith].
 */
enum class RunMode {
    DEFAULT,
    MEASURED,
    BENCHMARK,
}

/**
 * Represents AoC puzzle solution execution context.
 * @property input options specifying puzzle input source
 * @property runMode see [PuzzleRunner.runWith]
 * @property benchmark see [PuzzleRunner.runWith]
 */
interface Context {
    val input: Input
    val runMode: RunMode
    val benchmark: Benchmark

    /**
     * Basic settings for puzzle input retrieval...
     * @property root specifies a root folder of all puzzle input files
     * @property session AOC session value (used to download puzzle input
     * in case input file hasn't been found in the [root] folder).
     */
    data class Input(val root: String, val session: String?)

    /**
     * Benchmark specific settings...
     * @property loops number of iterations
     * @property warmUp number of iterations before actual measurement
     * @property limit max. duration of the benchmark
     * @see [PuzzleRunner.runWith]
     */
    data class Benchmark(val loops: Int, val warmUp: Int, val limit: Duration)
}

/**
 * Default AoC context. Use this one,
 * unless you need to achieve something
 * specific.
 * @property input puzzle input settings from [PropertyContext]
 * @property runMode run mode from [PropertyContext]
 * @property benchmark benchmark settings from [PropertyContext]
 */
object DefaultContext: Context {
    override val input: Context.Input
    override val runMode: RunMode
    override val benchmark: Context.Benchmark

    init {
        val default = PropertyContext.default()
        input = default.input
        runMode = default.runMode
        benchmark = default.benchmark
    }
}

/**
 * Primary / default implementation of the [Context] interface.
 *
 * Use [PropertyContext.default] and [PropertyContext.from] factory
 * methods to create new instances.
 *
 * @property input see [Context]
 * @property runMode see [Context]
 * @property benchmark see [Context]
 * @see DefaultContext
 */
class PropertyContext
    internal constructor(
        override val input: Context.Input,
        override val runMode: RunMode,
        override val benchmark: Context.Benchmark
    ): Context
{
    companion object {
        const val CONFIG_NAME = "config.properties"

        private val INPUT_ROOT = Property<String>(
            "puzzle.input.root",
            { it },
            { it.isNotBlank() },
            "./src/main/resources/inputs"
        )
        private val INPUT_SESSION = Property<String?>(
            "puzzle.input.session",
            { it },
            { it?.isNotBlank() ?: false },
            null
        )
        private val RUN_MODE = Property<RunMode>(
            "puzzle.run.mode",
            RunMode::valueOf,
            { true },
            RunMode.DEFAULT
        )
        private val BENCH_LOOPS = Property<Int>(
            "benchmark.iterations",
            { it.toInt() },
            { it in 2..1_000_000 },
            100
        )
        private val BENCH_WARMUP = Property<Int>(
            "benchmark.warmup",
            { it.toInt() },
            { it in 0..100 },
            1
        )
        private val BENCH_LIMIT_MINS = Property<Duration>(
            "benchmark.limit.minutes",
            { it.toInt().minutes },
            { it >= 1.minutes && it <= 8.hours },
            1.minutes
        )

        /**
         * Factory method which creates new [PropertyContext] instance
         * from the default sources (system properties, environment variables,
         * app property file or fall-back defaults).
         */
        fun default() = from()

        /**
         * Factory method which creates new [PropertyContext] instance
         * populating it from provided sources in a following order
         * - [properties]
         * - [variables]
         * - [propertyFileName]
         * - hardcoded fall-back
         * @param properties any [Properties] instance (by default Java system properties)
         * @param variables any Map<String, String> (by default System environment variables)
         * @param propertyFileName properties loaded from given file name
         */
        fun from(
            properties: Properties = systemProperties(),
            variables: Map<String, String> = systemEnv(),
            propertyFileName: String = CONFIG_NAME
        ): PropertyContext {
            val parser = PropertyParser(properties, variables, propertyFileName)
            val benchmark = Context.Benchmark(
                warmUp = parser.extract(BENCH_WARMUP),
                loops = parser.extract(BENCH_LOOPS),
                limit = parser.extract(BENCH_LIMIT_MINS)
            )
            val input = Context.Input(
                root = parser.extract(INPUT_ROOT),
                session = parser.extract(INPUT_SESSION)
            )
            return PropertyContext(
                input,
                parser.extract(RUN_MODE),
                benchmark
            )
        }

        private fun systemProperties() = System.getProperties() ?: Properties()
        private fun systemEnv() = System.getenv().orEmpty()
    }
}

/**
 * @property propId name of the system / project property
 * @property converter function converting String property
 * value to a desired type.
 * @property validator way to restrict boundaries of common
 * types
 * @property defaultValue fall-back value in case [PropertyParser.extract]
 * failed to retrieve one from the desired sources
 * @property envId name of the environment variable of the property
 */
private data class Property<T>(
    val propId: String,
    val converter: (String) -> T,
    val validator: (T) -> Boolean,
    val defaultValue: T
) {
    val envId: String = "AOC_${propId.replace('.', '_').uppercase()}"
}

/**
 * Implementation of prioritized property loading
 * from different sources (see use in [PropertyContext.from])
 */
private class PropertyParser(
    private val sysProps: Properties,
    private val envMap: Map<String, String>,
    propertyFile: String
) {
    private val appProps: Properties = loadProperties(propertyFile)

    fun <R> extract(property: Property<R>): R {
        val rawValue = sysProps[property.propId] ?: envMap[property.envId] ?: appProps[property.propId]
        return try {
            val propValue = property.converter(rawValue as String)
            if (property.validator(propValue)) propValue else property.defaultValue
        } catch (_: Exception) {
            // TODO: log warning
            property.defaultValue
        }
    }

    private fun loadProperties(propertyFileName: String): Properties =
        try {
            javaClass.classLoader
                .getResourceAsStream(propertyFileName).use {
                    Properties().apply { load(it) }
                }
        } catch (_: Exception) {
            //TODO: log warning
            Properties()
        }
}
