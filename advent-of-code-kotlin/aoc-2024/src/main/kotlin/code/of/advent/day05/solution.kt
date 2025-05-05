/**
 * Advent of Code 2024, day 5
 * https://adventofcode.com/2024/day/5
 */
package code.of.advent.day05

import code.of.advent.*

typealias RuleMap = Map<Int, Set<Int>>

/**
 * Wrapper for [RuleMap] and [Update] list.
 */
data class PrintQueue(val rules: RuleMap, val updates: List<Update>)

/**
 * Loader and parser of input data for [Day05] puzzle.
 * See [PuzzleRunner], [Puzzle] & [PuzzleInput].
 */
class Day05Input(context: Context): PuzzleInput<PrintQueue> {
    override val source: String = buildSourceWith(context)

    override fun loadFrom(fileName: String): PrintQueue =
        runWithFile(fileName) { file ->
            val lines = file.readLines().iterator()
            val rules = mutableMapOf<Int, MutableSet<Int>>()
            lines.asSequence().takeWhile { it.isNotEmpty() }
                .map { it.split('|').toIntList() }
                .forEach { rules.getOrPut(it[0], ::mutableSetOf).add(it[1]) }
            val updates = lines.asSequence()
                .map { it.split(',').toIntList() }
                .toList()
            return PrintQueue(rules, updates)
        }

    private fun List<String>.toIntList(): List<Int> = map { it.toInt() }
}

/**
 * Implementation of
 * [Advent of Code 2024 Day 5](https://adventofcode.com/2024/day/5)
 * puzzle.
 */
class Day05: Puzzle<PrintQueue, Int> {
    override val year = "2024"
    override val day = "Day 5"

    override fun part1(input: PrintQueue): Int =
        input.updates.filter { it.isCorrect(input.rules) }.sumOf { it[it.size / 2] }

    override fun part2(input: PrintQueue): Int =
        input.updates.filterNot { it.isCorrect(input.rules) }
            .map { it.toFixedUpdate(input.rules) }
            .sumOf { it[it.size / 2] }
}

fun main() {
    PrintRunner(Day05Input(DefaultContext), Day05()).runWith(DefaultContext)
}
