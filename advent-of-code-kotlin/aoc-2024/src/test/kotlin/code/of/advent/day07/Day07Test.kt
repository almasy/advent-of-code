package code.of.advent.day07

import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.longs.shouldBeExactly

@Tags(INTEGRATION)
class Day07Test: ShouldSpec ({

    context("Day07.part1") {
        val input = Day07Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day07().part1(input.load())
            actual shouldBeExactly DAY07_PART1
        }
    }

    context("Day07.part2") {
        val input = Day07Input(IntegrationContext)
        should("calculate expected puzzle result") {
            val actual = Day07().part2(input.load())
            actual shouldBeExactly DAY07_PART2
        }
    }

})
