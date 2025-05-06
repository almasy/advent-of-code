/**
 * Advent of Code 2024, day 7
 * https://adventofcode.com/2024/day/7
 */
package code.of.advent.day07

import code.of.advent.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

/**
 * Loader and parser of input data for [Day07] puzzle.
 * See [PuzzleRunner], [Puzzle] & [PuzzleInput].
 */
class Day07Input(context: Context): PuzzleInput<List<Equation>> {
    override val source: String = buildSourceWith(context)

    override fun loadFrom(fileName: String): List<Equation> =
        runWithFile(fileName) { file ->
            file.useLines { lines ->
                lines.map { it.split(':') }
                    .map { it[0] to it[1].trim().split(' ') }
                    .map { it.first.toLong() to it.second.toLongList() }
                    .map { Equation(it.first, it.second) }
                    .toList()
            }
        }

    private fun List<String>.toLongList(): List<Long> = map { it.toLong() }
}

/**
 * Implementation of
 * [Advent of Code 2024 Day 7](https://adventofcode.com/2024/day/7)
 * puzzle.
 */
class Day07: Puzzle<List<Equation>, Long> {
    override val year = "2024"
    override val day = "Day 7"

    override fun part1(input: List<Equation>): Long =
        input.filter { it.hasSolution(PART_1_OPS) } .sumOf { it.result }

    override fun part2(input: List<Equation>): Long =
        runBlocking(Dispatchers.Default) {
            input
                .map { async { it.hasSolution(PART_2_OPS) to it } }
                .awaitAll()
                .filter { it.first }
                .sumOf { it.second.result }
        }

    private companion object {
        private val PART_1_OPS = listOf(Operator.ADD, Operator.MUL)
        private val PART_2_OPS = listOf(Operator.MUL, Operator.ADD, Operator.JOIN)
    }
}

fun main() {
    PrintRunner(Day07Input(DefaultContext), Day07()).runWith(DefaultContext)
}
