/**
 * Advent of Code 2024, day 3
 * https://adventofcode.com/2024/day/3
 */
package code.of.advent.day03

import code.of.advent.*

/**
 * Loader and parser of input data for [Day03] puzzle.
 * See [PuzzleRunner], [Puzzle] & [PuzzleInput].
 */
class Day03Input(context: Context): PuzzleInput<List<Instruction>> {
    override val source: String = buildSourceWith(context)

    override fun loadFrom(fileName: String): List<Instruction> =
        runWithFile(fileName) { file ->
            file.readLines().flatMap {
                REGEX.findAll(it).map { it.value }
            }
        }

    private companion object {
        private val REGEX = "mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)".toRegex()
    }
}

/**
 * Implementation of
 * [Advent of Code 2024 Day 3](https://adventofcode.com/2024/day/3)
 * puzzle.
 */
class Day03: Puzzle<List<Instruction>, Long> {
    override val year = "2024"
    override val day = "Day 3"

    override fun part1(input: List<Instruction>): Long =
        input.filter { mulFilter(it) }.sumOf { it.evaluate() }

    override fun part2(input: List<Instruction>): Long {
        val onOffFilter = OnOffFilter() // it's state-full -> needs an instance
        return input.filter { onOffFilter(it) }.sumOf { it.evaluate() }
    }
}

fun main() {
    PrintRunner(Day03Input(DefaultContext), Day03()).runWith(DefaultContext)
}
