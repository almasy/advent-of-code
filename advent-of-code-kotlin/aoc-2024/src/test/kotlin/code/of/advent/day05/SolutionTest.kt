package code.of.advent.day05

import code.of.advent.INTEGRATION
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.system.captureStandardOut
import io.kotest.matchers.string.shouldContainInOrder

@Tags(INTEGRATION)
class SolutionTest : ShouldSpec({

    context("day05.Solution.main") {
        should("print proper part1 & part2 results") {
            captureStandardOut { main() }
                .shouldContainInOrder(
                    "Advent of Code 2024 Day 5",
                    "Puzzle input source:",
                    "Part1: $DAY05_PART1",
                    "Part2: $DAY05_PART2"
                )
        }
    }

})
