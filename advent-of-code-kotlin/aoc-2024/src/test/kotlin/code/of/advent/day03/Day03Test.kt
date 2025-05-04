package code.of.advent.day03

import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.longs.shouldBeExactly

@Tags(INTEGRATION)
class Day03Test : ShouldSpec({

    context("Day03.part1") {
        val input = Day03Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day03().part1(input.load())
            actual shouldBeExactly DAY03_PART1
        }
    }

    context("Day03.part2") {
        val input = Day03Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day03().part2(input.load())
            actual shouldBeExactly DAY03_PART2
        }
    }

})
