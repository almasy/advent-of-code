package code.of.advent.day05

import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeExactly

@Tags(INTEGRATION)
class Day05Test : ShouldSpec({

    context("Day05.part1") {
        val input = Day05Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day05().part1(input.load())
            actual shouldBeExactly DAY05_PART1
        }
    }

    context("Day05.part2") {
        val input = Day05Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day05().part2(input.load())
            actual shouldBeExactly DAY05_PART2
        }
    }

})
