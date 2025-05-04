package code.of.advent.day02

import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeExactly

@Tags(INTEGRATION)
class Day02Test : ShouldSpec({

    context("Day02.part1") {
        val input = Day02Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day02().part1(input.load())
            actual shouldBeExactly DAY02_PART1
        }
    }

    context("Day02.part2") {
        val input = Day02Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day02().part2(input.load())
            actual shouldBeExactly DAY02_PART2
        }
    }

})
