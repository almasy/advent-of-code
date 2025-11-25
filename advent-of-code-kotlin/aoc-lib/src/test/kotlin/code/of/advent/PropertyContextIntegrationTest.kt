package code.of.advent

import io.kotest.assertions.assertSoftly
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.system.withSystemProperties
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.notExists
import kotlin.time.Duration.Companion.minutes

@Tags("Integration")
class PropertyContextIntegrationTest : ShouldSpec({

    context("PropertyContext.default") {
        val configExists = Path(DEFAULT_CONFIG_FILE).exists()

        should("use default project property file").config(enabledIf = { configExists }) {
            assertSoftly(PropertyContext.default()) {
                input.root shouldBe "./src/test/resources/inputs"
                input.session shouldBe "XABCDEFGHZ"
                runMode shouldBe RunMode.MEASURED
                benchmark.warmUp shouldBe 2
                benchmark.limit shouldBe 2.minutes
                benchmark.loops shouldBe 20
            }
        }

        should("use system properties before project property file").config(enabledIf = { configExists }) {
            val properties = Properties().apply {
                setProperty("puzzle.input.session", "11223344")
                setProperty("puzzle.run.mode", "BENCHMARK")
                setProperty("benchmark.warmup", "10")
                setProperty("benchmark.iterations", "99")
            }
            val actual = withSystemProperties(properties) { PropertyContext.default() }
            assertSoftly(actual) {
                input.root shouldBe "./src/test/resources/inputs"
                input.session shouldBe "11223344"
                runMode shouldBe RunMode.BENCHMARK
                benchmark.warmUp shouldBe 10
                benchmark.limit shouldBe 2.minutes
                benchmark.loops shouldBe 99
            }
        }
    }

    context("DefaultContext") {
        val configExists = Path(DEFAULT_CONFIG_FILE).exists()

        should("contain values from default property file").config(enabledIf = { configExists }) {
            assertSoftly(DefaultContext) {
                input.root shouldBe "./src/test/resources/inputs"
                input.session shouldBe "XABCDEFGHZ"
                runMode shouldBe RunMode.MEASURED
                benchmark.warmUp shouldBe 2
                benchmark.limit shouldBe 2.minutes
                benchmark.loops shouldBe 20
            }
        }
    }

    context("PropertyContext.from given non-existent file") {
        val properties = Properties()
        val variables = emptyMap<String, String>()
        val configName = "does_not_exist.properties"

        should("use fall-back default values").config(enabledIf = { Path(configName).notExists() }) {
            assertSoftly(PropertyContext.from(properties, variables, configName)) {
                input.root shouldBe "./src/main/resources/inputs"
                input.session.shouldBeNull()
                runMode shouldBe RunMode.DEFAULT
                benchmark.warmUp shouldBe 1
                benchmark.limit shouldBe 1.minutes
                benchmark.loops shouldBe 100
            }
        }
    }

    context("PropertyContext.from given property file with invalid value") {
        val properties = Properties()
        val variables = emptyMap<String, String>()
        val runModeProps = "./src/test/resources/invalid_runMode.properties"
        val loopsProps = "./src/test/resources/invalid_loops.properties"
        val limitProps = "./src/test/resources/invalid_limit.properties"
        val warmUpProps = "./src/test/resources/invalid_warmup.properties"

        should("use fall-back default puzzle.runMode").config(enabledIf = { Path(runModeProps).exists() }) {
            val actual = PropertyContext.from(properties, variables, runModeProps)
            actual.runMode shouldBe RunMode.DEFAULT
        }

        should("use fall-back default benchmark.loops").config(enabledIf = { Path(loopsProps).exists() }) {
            val actual = PropertyContext.from(properties, variables, loopsProps)
            actual.benchmark.loops shouldBe 100
        }

        should("use fall-back default benchmark.limit").config(enabledIf = { Path(limitProps).exists() }) {
            val actual = PropertyContext.from(properties, variables, limitProps)
            actual.benchmark.limit shouldBe 1.minutes
        }
        should("use fall-back default benchmark.warmUp").config(enabledIf = { Path(warmUpProps).exists() }) {
            val actual = PropertyContext.from(properties, variables, limitProps)
            actual.benchmark.warmUp shouldBe 1
        }
    }

})
