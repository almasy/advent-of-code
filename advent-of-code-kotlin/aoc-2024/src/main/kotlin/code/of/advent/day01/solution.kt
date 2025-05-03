/**
 * Advent of Code 2024, day 1
 * https://adventofcode.com/2024/day/1
 */
package code.of.advent.day01

import code.of.advent.*
import kotlin.math.abs

private typealias ListPair = Pair<List<Int>, List<Int>>

/**
 * Loader and parser of input data for [Day01] puzzle.
 * @see PuzzleRunner
 * @see Puzzle
 * @see PuzzleInput
 */
class Day01Input(context: Context): PuzzleInput<ListPair> {
    override val source: String = buildSourceWith(context)

    override fun loadFrom(fileName: String): ListPair =
        runWithFile(fileName) { file ->
            file.readLines().map { line ->
                val (left, right) = REGEX.split(line)
                left.toInt() to right.toInt()
            }.unzip()
        }

    private companion object {
        private val REGEX = "\\W+".toRegex()
    }
}

/**
 * Implementation of
 * [Advent of Code 2024 Day 1](https://adventofcode.com/2024/day/1)
 * puzzle.
 */
class Day01: Puzzle<ListPair, Long> {
    override val year = "2024"
    override val day = "Day 1"

    override fun part1(input: ListPair): Long =
        input.first.sorted().zip(input.second.sorted()).sumOf {
            abs(it.first - it.second).toLong()
        }

    override fun part2(input: ListPair): Long {
        val counts = input.first.groupingBy { it }.eachCount()
        return input.second.sumOf { it.toLong() * (counts[it] ?: 0) }
    }
}

fun main() {
    PrintRunner(Day01Input(DefaultContext), Day01()).runWith(DefaultContext)
}
