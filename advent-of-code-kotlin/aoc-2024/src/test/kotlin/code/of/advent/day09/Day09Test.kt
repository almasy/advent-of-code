package code.of.advent.day09

import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.longs.shouldBeExactly

@Tags(INTEGRATION)
class Day09Test : ShouldSpec({

    context("Day09.part1") {
        val input = Day09Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day09().part1(input.load())
            actual shouldBeExactly DAY09_PART1
        }
    }

    context("Day09.part2") {
        val input = Day09Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day09().part2(input.load())
            actual shouldBeExactly DAY09_PART2
        }
    }

})
