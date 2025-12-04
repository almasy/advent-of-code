/**
 * T.B.D.
 */
package templates

fun solutionTemplate(
    classPackage: String,
    year: String,
    day: String,
    dayNumber: Int) =
"""/**
 * Advent of Code $year, Day $dayNumber
 * https://adventofcode.com/$year/day/$dayNumber
 */
package $classPackage

import code.of.advent.*

/**
 * Loader and parser of input data for [$day] puzzle.
 * TODO: Remember to change generic type to your desired puzzle input type
 * @see PuzzleRunner
 * @see Puzzle
 * @see PuzzleInput
 */
class ${day}Input(context: Context): PuzzleInput<Any> {
    override val source: String = buildSourceWith(context)

    override fun loadFrom(fileName: String): Any =
        TODO("Your implementation of puzzle input parser goes here!")
}

/**
 * Implementation of
 * [Advent of Code $year, Day $dayNumber](https://adventofcode.com/$year/day/$dayNumber)
 * puzzle.
 *
 * TODO: Remember to change generic types to your desired puzzle input and output types
 */
class ${day}: Puzzle<Any, Any> {
    override val year = "$year"
    override val day = "$day"

    override fun part1(input: Any): Any =
        TODO("Your implementation of puzzle solution goes here!")

    override fun part2(input: Any): Any =
        TODO("Your implementation of puzzle solution goes here!")
}

fun main() {
    PrintRunner(${day}Input(DefaultContext), ${day}()).runWith(DefaultContext)
}
"""
