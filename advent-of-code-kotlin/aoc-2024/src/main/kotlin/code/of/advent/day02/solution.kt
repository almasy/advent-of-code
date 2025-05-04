/**
 * Advent of Code 2024, day 2
 * https://adventofcode.com/2024/day/2
 */
package code.of.advent.day02

import code.of.advent.*

/**
 * Loader and parser of input data for [Day02] puzzle.
 * See [PuzzleRunner], [Puzzle] & [PuzzleInput].
 */
class Day02Input(context: Context): PuzzleInput<List<Report>> {
    override val source: String = buildSourceWith(context)

    override fun loadFrom(fileName: String): List<Report> =
        runWithFile(fileName) { file ->
            file.readLines().map { line ->
                REGEX.split(line).map { it.toInt() }
            }.onEach { require(it.size > 1) }
        }

    private companion object {
        private val REGEX = "\\W+".toRegex()
    }
}

/**
 * Implementation of
 * [Advent of Code 2024 Day 2](https://adventofcode.com/2024/day/2)
 * puzzle.
 */
class Day02: Puzzle<List<Report>, Int> {
    override val year = "2024"
    override val day = "Day 2"

    override fun part1(input: List<Report>): Int =
        input.count { it.safe() }

    override fun part2(input: List<Report>): Int =
        input.count { it.safeWithDampener() }
}

fun main() {
    PrintRunner(Day02Input(DefaultContext), Day02()).runWith(DefaultContext)
}
