package code.of.advent.day06

import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeExactly

@Tags(INTEGRATION)
class Day06Test : ShouldSpec({

    context("Day06.part1") {
        val input = Day06Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day06().part1(input.load())
            actual shouldBeExactly DAY06_PART1
        }
    }

    context("Day06.part2") {
        val input = Day06Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day06().part2(input.load())
            actual shouldBeExactly DAY06_PART2
        }
    }

})
