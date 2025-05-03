package code.of.advent.day01

import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.longs.shouldBeExactly
import org.junit.jupiter.api.Tag

@Tag(INTEGRATION)
class Day01Test : ShouldSpec({

    context("Day01.part1") {
        val input = Day01Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day01().part1(input.load())
            actual shouldBeExactly DAY01_PART1
        }
    }

    context("Day01.part2") {
        val input = Day01Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day01().part2(input.load())
            actual shouldBeExactly DAY01_PART2
        }
    }

})
