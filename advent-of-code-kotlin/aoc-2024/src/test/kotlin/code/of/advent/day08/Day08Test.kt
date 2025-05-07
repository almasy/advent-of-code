package code.of.advent.day08

import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeExactly

@Tags(INTEGRATION)
class Day08Test : ShouldSpec({

    context("Day08.part1") {
        val input = Day08Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day08().part1(input.load())
            actual shouldBeExactly DAY08_PART1
        }
    }

    context("Day08.part2") {
        val input = Day08Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day08().part2(input.load())
            actual shouldBeExactly DAY08_PART2
        }
    }

})
