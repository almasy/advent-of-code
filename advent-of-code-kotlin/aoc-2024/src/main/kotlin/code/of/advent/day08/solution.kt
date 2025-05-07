/**
 * Advent of Code 2024, day 8
 * https://adventofcode.com/2024/day/8
 */
package code.of.advent.day08

import code.of.advent.*
import kotlin.math.max

/**
 * Auxiliary datatype used by [Antennas].
 * @property x
 * @property y
 */
data class Position(val x: Int, val y: Int) {
    operator fun plus(other: Position): Position =
        Position(this.x + other.x, this.y + other.y)
    operator fun minus(other: Position): Position =
        Position(this.x - other.x, this.y - other.y)
}

/**
 * Loader and parser of input data for [Day08] puzzle.
 * See [PuzzleRunner], [Puzzle] & [PuzzleInput].
 */
class Day08Input(context: Context): PuzzleInput<Antennas> {
    override val source: String = buildSourceWith(context)

    /** Slightly over-the-top "functional style" puzzle loading */
    override fun loadFrom(fileName: String): Antennas =
        runWithFile(fileName) { file ->
            var sizeX = 0
            var sizeY = 0
            val antennas = file.bufferedReader().use { reader ->
                reader.lineSequence()
                .withIndex()
                .onEach { (y, _) -> sizeY = y }
                .flatMap { (y, line) ->
                    line.withIndex()
                        .onEach { (x, _) -> sizeX = max(x, sizeX) }
                        .filter { (_, char) -> char != EMPTY_SLOT }
                        .map { (x, char) -> char to Position(x, y) }
                }
                .groupBy(keySelector = { it.first }, valueTransform = { it.second })
            }
            return Antennas(antennas, sizeX = sizeX, sizeY = sizeY)
        }

    private companion object {
        private const val EMPTY_SLOT = '.'
    }
}

/**
 * Implementation of
 * [Advent of Code 2024 Day 8](https://adventofcode.com/2024/day/8)
 * puzzle.
 */
class Day08: Puzzle<Antennas, Int> {
    override val year = "2024"
    override val day = "Day 8"

    override fun part1(input: Antennas): Int =
        input.antiNodes().toSet().size

    override fun part2(input: Antennas): Int =
        input.resonantAntiNodes().toSet().size
}

fun main() {
    PrintRunner(Day08Input(DefaultContext), Day08()).runWith(DefaultContext)
}
