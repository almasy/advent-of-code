/**
 * Advent of Code 2024, day 4
 * https://adventofcode.com/2024/day/4
 */
package code.of.advent.day04

import code.of.advent.*

/**
 * Loader and parser of input data for [Day04] puzzle.
 * See [PuzzleRunner], [Puzzle] & [PuzzleInput].
 */
class Day04Input(context: Context): PuzzleInput<LetterMatrix> {
    override val source: String = buildSourceWith(context)

    override fun loadFrom(fileName: String): LetterMatrix =
        runWithFile(fileName) {
            LetterMatrix(it.readLines().map(String::toList))
        }
}

/**
 * Implementation of
 * [Advent of Code 2024 Day 4](https://adventofcode.com/2024/day/4)
 * puzzle.
 */
class Day04: Puzzle<LetterMatrix, Long> {
    override val year = "2024"
    override val day = "Day 4"

    override fun part1(input: LetterMatrix): Long =
        input.positions()
            .filter { p -> input[p] == 'X' }
            .map { p -> input.countXmasAt(p) }
            .sum()

    override fun part2(input: LetterMatrix): Long =
        input.positions(padX = 1, padY = 1)
            .filter { p -> input[p] == 'A' }
            .map { p -> input.countMasAt(p) }
            .sum()
}

fun main() {
    PrintRunner(Day04Input(DefaultContext) ,Day04()).runWith(DefaultContext)
}
