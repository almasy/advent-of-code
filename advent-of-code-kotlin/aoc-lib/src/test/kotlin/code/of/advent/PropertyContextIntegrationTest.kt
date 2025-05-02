package code.of.advent

import io.kotest.assertions.assertSoftly
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.system.withSystemProperties
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
                inputRoot shouldBe "./src/test/resources/inputs"
                runMode shouldBe RunMode.MEASURED
                benchmark.limit shouldBe 2.minutes
                benchmark.loops shouldBe 20
            }
        }

        should("use system properties before project property file").config(enabledIf = { configExists }) {
            val properties = Properties().apply {
                setProperty("puzzle.run.mode", "BENCHMARK")
                setProperty("benchmark.iterations", "99")
            }
            val actual = withSystemProperties(properties) { PropertyContext.default() }
            assertSoftly(actual) {
                inputRoot shouldBe "./src/test/resources/inputs"
                runMode shouldBe RunMode.BENCHMARK
                benchmark.limit shouldBe 2.minutes
                benchmark.loops shouldBe 99
            }
        }
    }

    context("DefaultContext") {
        val configExists = Path(DEFAULT_CONFIG_FILE).exists()

        should("contain values from default property file").config(enabledIf = { configExists }) {
            assertSoftly(DefaultContext) {
                inputRoot shouldBe "./src/test/resources/inputs"
                runMode shouldBe RunMode.MEASURED
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
                inputRoot shouldBe "./src/main/resources/inputs"
                runMode shouldBe RunMode.DEFAULT
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

        should("use fall-back default runMode").config(enabledIf = { Path(runModeProps).exists() }) {
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
    }

})
