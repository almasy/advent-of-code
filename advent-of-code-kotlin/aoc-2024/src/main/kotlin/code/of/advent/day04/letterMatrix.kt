/**
 * Auxiliary functions and/or datatypes
 * for [Day04] puzzle solution.
 */
package code.of.advent.day04

/**
 * Auxiliary data structure used in [LetterMatrix].
 * @property x horizontal position (i.e. column)
 * @property y vertical position (i.e. row)
 */
data class Position(val x: Int, val y: Int) {
    operator fun plus(other: Position): Position =
        Position(x + other.x, y + other.y)
}

typealias Direction = Position

/**
 * Central datatype of the [Day04] solution. See [countXmasAt]
 * and [countMasAt].
 * @property letterMatrix all letters of the puzzle input
 * @property sizeX total number of columns
 * @property sizeY total number of rows
 */
class LetterMatrix(private val letterMatrix: List<List<Char>>) {
    private val sizeX: Int
    private val sizeY: Int

    init {
        require(letterMatrix.isNotEmpty())
        require(letterMatrix.first().isNotEmpty())
        sizeX = letterMatrix.first().size
        sizeY = letterMatrix.size
    }

    operator fun contains(position: Position) =
        position.x >= 0 && position.x < sizeX && position.y >= 0 && position.y < sizeY

    /**
     * Helps to iterate through the [letterMatrix] by generating
     * a sequence of [Position] items, each representing unique letter
     * position. Starts at top-left and ends at bottom-right corner.
     */
    fun positions(padX: Int = 0, padY: Int = 0): Sequence<Position> =
        (padY..<sizeY-padY).asSequence()
            .flatMap { y->
                (padX..<sizeX-padX).asSequence()
                    .map { x-> Position(x, y) }
            }

    operator fun get(position: Position) = letterMatrix[position.y][position.x]

    /**
     * Checks for the presence of "XMAS" letters in all directions
     * starting at given [position] (assuming the [position]
     * contains letter "X").
     * @return count of all directions containing "XMAS"
     */
    fun countXmasAt(position: Position): Long =
        XMAS_DELTAS.sumOf { directions -> countXmasAt(position, directions) }

    /**
     * Checks for presence of "XMAS" letters in given [directions]
     * starting at given [position] (assuming the [position] contains
     * letter "X").
     */
    private fun countXmasAt(position: Position, directions: List<Direction>): Long {
        val actual = directions
            .map { d -> position + d  }
            .filter { p -> p in this }
            .map { p -> this[p] }
        return if (actual == MAS) 1 else 0
    }

    /**
     * Checks, whether given [position] contains letters "MAS"
     * crossing in both diagonals (assuming [position] contains
     * letter 'A').
     * @return 1 if [position] is a center of cross-MAS, 0 otherwise
     */
    fun countMasAt(position: Position): Long =
        X_MAS_DELTAS
            .map { d -> d.map { position + it } .map { this[it] } }
            .map { countIfMas(it) }
            .reduce { a, b -> a * b }

    private fun countIfMas(letters: List<Char>): Long =
        when (letters) {
            MS, SM -> 1
            else -> 0
        }

    private companion object {
        private val MAS = "MAS".toList()
        private val MS = "MS".toList()
        private val SM = MS.reversed()

        // defined just as a shorter alias...
        private fun dir(x: Int, y: Int) = Direction(x, y)

        private val XMAS_DELTAS = listOf(
            listOf(dir( 1,  0), dir( 2,  0), dir( 3,  0)), // horizontal
            listOf(dir(-1,  0), dir(-2,  0), dir(-3,  0)),
            listOf(dir( 0,  1), dir( 0,  2), dir( 0,  3)), // vertical
            listOf(dir( 0, -1), dir( 0, -2), dir( 0, -3)),
            listOf(dir( 1,  1), dir( 2,  2), dir( 3,  3)), // diagonal
            listOf(dir(-1, -1), dir(-2, -2), dir(-3, -3)),
            listOf(dir(-1,  1), dir(-2,  2), dir(-3,  3)),
            listOf(dir( 1, -1), dir( 2, -2), dir( 3, -3))
        )
        private val X_MAS_DELTAS = listOf(
            listOf(dir(-1, -1), dir(1, 1)),
            listOf(dir(-1,  1), dir(1, -1))
        )
    }
}
