/**
 * Advent of Code 2024, day 6
 * https://adventofcode.com/2024/day/6
 */
package code.of.advent.day06

import code.of.advent.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

/**
 * Loader and parser of input data for [Day06] puzzle.
 * See [PuzzleRunner], [Puzzle] & [PuzzleInput].
 */
class Day06Input(context: Context): PuzzleInput<AreaMap> {
    override val source: String = buildSourceWith(context)

    override fun loadFrom(fileName: String): AreaMap =
        runWithFile(fileName) { file ->
            AreaMap(file.readLines().map { it.toList() })
        }
}

/**
 * Implementation of
 * [Advent of Code 2024 Day 6](https://adventofcode.com/2024/day/6)
 * puzzle.
 */
class Day06: Puzzle<AreaMap, Int> {
    override val year = "2024"
    override val day = "Day 6"

    override fun part1(input: AreaMap): Int =
        input.walkPositions().size

    override fun part2(input: AreaMap): Int {
        val visited = input.walkPositions() - input.guard.position
        return runBlocking(Dispatchers.Default) {
            visited
                .map { async { input.hasLoopWith(it) } }
                .awaitAll()
                .filter { it }
                .size
        }
    }
}

fun main() {
    PrintRunner(Day06Input(DefaultContext) ,Day06()).runWith(DefaultContext)
}
