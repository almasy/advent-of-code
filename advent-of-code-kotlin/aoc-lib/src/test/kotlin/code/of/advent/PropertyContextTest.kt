package code.of.advent

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.util.*
import kotlin.time.Duration.Companion.minutes

class PropertyContextTest : ShouldSpec({

    context("PropertyContext.load with properties and environment variables") {
        val environment = envMapOf(
            inputRoot = "./yet/another/path",
            inputSession = "XABCDEFGZ",
            runMode =  "BENCHMARK",
            warmUp = "9",
            loops = "686",
            limit = "15")

        should("use system properties as primary source") {
            val sysProps = propertiesOf(
                inputRoot = "./just/some/path",
                inputSession = "QWERTYUIOP",
                runMode = "MEASURED",
                warmUp = "3",
                loops = "545",
                limit = "20")
            val actual = PropertyContext.from(sysProps, environment)
            assertSoftly(actual) {
                input.root shouldBe "./just/some/path"
                input.session shouldBe "QWERTYUIOP"
                runMode shouldBe RunMode.MEASURED
                benchmark.warmUp shouldBe 3
                benchmark.loops shouldBe 545
                benchmark.limit shouldBe 20.minutes
            }
        }

        should("use environment variables as secondary source") {
            val actual = PropertyContext.from(Properties(), environment)
            assertSoftly(actual) {
                input.root shouldBe "./yet/another/path"
                input.session shouldBe "XABCDEFGZ"
                runMode shouldBe RunMode.BENCHMARK
                benchmark.warmUp shouldBe 9
                benchmark.loops shouldBe 686
                benchmark.limit shouldBe 15.minutes
            }
        }
    }

    context("PropertyContext.load with invalid properties") {
        val sysProps = propertiesOf(
            inputRoot = "   ",
            inputSession = "   ",
            runMode = "DOES_NOT_EXIST",
            warmUp = "NaN",
            loops = "NaN",
            limit = "NaN")
        should("use default fall-back values") {
            val actual = PropertyContext.from(sysProps)
            assertSoftly(actual) {
                input.root shouldBe DEFAULT_INPUT_ROOT
                input.session shouldBe DEFAULT_INPUT_SESSION
                runMode shouldBe DEFAULT_RUN_MODE
                benchmark.warmUp shouldBe DEFAULT_WARMUP
                benchmark.limit shouldBe DEFAULT_DURATION
                benchmark.loops shouldBe DEFAULT_LOOPS
            }
        }
    }

    context("PropertyContext.load with invalid environment variables") {
        val environment = envMapOf(
            inputRoot = "   ",
            inputSession = "   ",
            runMode = "DOES_NOT_EXIST",
            warmUp = "NaN",
            loops = "NaN",
            limit = "NaN")
        should("use default fall-back values") {
            val actual = PropertyContext.from(Properties(), environment)
            assertSoftly(actual) {
                input.root shouldBe DEFAULT_INPUT_ROOT
                input.session shouldBe DEFAULT_INPUT_SESSION
                runMode shouldBe DEFAULT_RUN_MODE
                benchmark.warmUp shouldBe DEFAULT_WARMUP
                benchmark.limit shouldBe DEFAULT_DURATION
                benchmark.loops shouldBe DEFAULT_LOOPS
            }
        }
    }

    context("PropertyContext.load should use default fall-back values") {
        val someRunMode = "DEFAULT"
        val someSession = "12345"
        val someWarmUp = "2"
        val someLoops = "10"
        val someLimit = "1"

        withData(
            nameFn = { "given out-of bounds benchmark.warmup=${it}" },
            "-2",
            "-1",
            "101",
        ) { warmUp ->
            val properties = propertiesOf(
                inputRoot = DEFAULT_INPUT_ROOT,
                inputSession = someSession,
                runMode = someRunMode,
                warmUp = warmUp,
                loops = someLoops,
                limit = someLimit)
            val actual = PropertyContext.from(properties)
            assertSoftly(actual) {
                benchmark.warmUp shouldBe DEFAULT_WARMUP
            }
        }

        withData(
            nameFn = { "given out-of bounds benchmark.iterations=${it}" },
            "-1",
            "0",
            "1",
            "1000001",
        ) { loops ->
            val properties = propertiesOf(
                inputRoot = DEFAULT_INPUT_ROOT,
                inputSession = someSession,
                runMode = someRunMode,
                warmUp = someWarmUp,
                loops = loops,
                limit = someLimit)
            val actual = PropertyContext.from(properties)
            assertSoftly(actual) {
                benchmark.loops shouldBe DEFAULT_LOOPS
            }
        }

        withData(
            nameFn = { "given out-of bounds benchmark.limit.minutes=${it}" },
            "-1",
            "0",
            "481",
        ) { limit ->
            val properties = propertiesOf(
                inputRoot = DEFAULT_INPUT_ROOT,
                inputSession = someSession,
                runMode = someRunMode,
                warmUp = someWarmUp,
                loops = someLoops,
                limit = limit)
            val actual = PropertyContext.from(properties)
            assertSoftly(actual) {
                benchmark.limit shouldBe DEFAULT_DURATION
            }
        }

    }

})
