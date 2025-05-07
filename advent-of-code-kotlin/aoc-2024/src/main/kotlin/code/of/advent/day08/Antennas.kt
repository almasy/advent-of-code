package code.of.advent.day08

/**
 * Central datatype of [Day08] solution. See [antiNodes]
 * and [resonantAntiNodes].
 */
class Antennas(
    private val antennas: Map<Char, List<Position>>,
    private val sizeX: Int,
    private val sizeY: Int
) {

    init {
        require(sizeY > 0)
        require(sizeX > 0)
    }

    operator fun contains(position: Position): Boolean =
        position.x in 0..sizeX && position.y in 0..sizeY

    private fun combinations(positions: List<Position>): Sequence<Pair<Position, Position>> =
        positions.asSequence().withIndex().flatMap { (i, p) ->
            generateSequence { p } .zip(positions.asSequence().drop(i + 1))
        }

    private fun antennaPairs(): Sequence<Pair<Position, Position>> =
        antennas.values.asSequence().flatMap { p -> combinations(p) }

    /**
     * Creates two anti-nodes for every pair of same-type
     * antennas. Anti-nodes out of the map boundaries ([sizeX], [sizeY])
     * are filtered-out of the resulting sequence.
     */
    fun antiNodes(): Sequence<Position> =
        antennaPairs()
            .flatMap { (p1, p2) -> sequenceOf(p1 + (p1 - p2), p2 + (p2 - p1)) }
            .filter { it in this@Antennas }

    private fun resonances(antenna: Position, delta: Position): Sequence<Position> =
        generateSequence(antenna) { it + delta  } .takeWhile { it in this@Antennas }

    /**
     * Creates all anti-nodes and their resonances (i.e. anti-nodes on
     * a line given by the positions of the two antennas) for every pair
     * of the same-type antennas. Anti-nodes out of the map boundaries
     * ([sizeX], [sizeY]) are filtered-out of the resulting sequence.
     */
    fun resonantAntiNodes(): Sequence<Position> =
        antennaPairs()
            .flatMap { (p1, p2) ->
                resonances(p1, p1 - p2) + resonances(p2, p2 - p1)
            }
}
