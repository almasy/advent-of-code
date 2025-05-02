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
            runMode =  "BENCHMARK",
            loops = "686",
            limit = "15")

        should("use system properties as primary source") {
            val sysProps = propertiesOf("./just/some/path", "MEASURED", "545", "20")
            val actual = PropertyContext.from(sysProps, environment)
            assertSoftly(actual) {
                runMode shouldBe RunMode.MEASURED
                inputRoot shouldBe "./just/some/path"
                benchmark.limit shouldBe 20.minutes
                benchmark.loops shouldBe 545
            }
        }

        should("use environment variables as secondary source") {
            val actual = PropertyContext.from(Properties(), environment)
            assertSoftly(actual) {
                runMode shouldBe RunMode.BENCHMARK
                inputRoot shouldBe "./yet/another/path"
                benchmark.limit shouldBe 15.minutes
                benchmark.loops shouldBe 686
            }
        }
    }

    context("PropertyContext.load with invalid properties") {
        val sysProps = propertiesOf("   ", "DOES_NOT_EXIST", "NaN", "NaN")
        should("use default fall-back values") {
            val actual = PropertyContext.from(sysProps)
            assertSoftly(actual) {
                runMode shouldBe DEFAULT_RUN_MODE
                inputRoot shouldBe DEFAULT_INPUT_ROOT
                benchmark.limit shouldBe DEFAULT_DURATION
                benchmark.loops shouldBe DEFAULT_LOOPS
            }
        }
    }

    context("PropertyContext.load with invalid environment variables") {
        val environment = envMapOf("   ", "DOES_NOT_EXIST", "NaN", "NaN")
        should("use default fall-back values") {
            val actual = PropertyContext.from(Properties(), environment)
            assertSoftly(actual) {
                runMode shouldBe DEFAULT_RUN_MODE
                inputRoot shouldBe DEFAULT_INPUT_ROOT
                benchmark.limit shouldBe DEFAULT_DURATION
                benchmark.loops shouldBe DEFAULT_LOOPS
            }
        }
    }

    context("PropertyContext.load should use default fall-back values") {

        withData(
            nameFn = { "given out-of bounds benchmark.iterations=${it}" },
            "-1",
            "0",
            "1",
            "1000001",
        ) { loops ->
            val properties = propertiesOf(DEFAULT_INPUT_ROOT, "DEFAULT", loops, "1")
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
            val properties = propertiesOf(DEFAULT_INPUT_ROOT, "DEFAULT", "100", limit)
            val actual = PropertyContext.from(properties)
            assertSoftly(actual) {
                benchmark.limit shouldBe DEFAULT_DURATION
            }
        }

    }

})
