package code.of.advent.day04

import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.longs.shouldBeExactly

@Tags(INTEGRATION)
class Day04Test : ShouldSpec({

    context("Day04.part1") {
        val input = Day04Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day04().part1(input.load())
            actual shouldBeExactly DAY04_PART1
        }
    }

    context("Day04.part2") {
        val input = Day04Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day04().part2(input.load())
            actual shouldBeExactly DAY04_PART2
        }
    }

})
