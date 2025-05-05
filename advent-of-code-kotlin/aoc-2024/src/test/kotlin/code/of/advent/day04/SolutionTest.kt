package code.of.advent.day04

import code.of.advent.INTEGRATION
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.system.captureStandardOut
import io.kotest.matchers.string.shouldContainInOrder

@Tags(INTEGRATION)
class SolutionTest : ShouldSpec({

    context("day04.Solution.main") {
        should("print proper part1 & part2 results") {
            captureStandardOut { main() }
                .shouldContainInOrder(
                    "Advent of Code 2024 Day 4",
                    "Puzzle input source:",
                    "Part1: $DAY04_PART1",
                    "Part2: $DAY04_PART2"
                )
        }
    }

})
