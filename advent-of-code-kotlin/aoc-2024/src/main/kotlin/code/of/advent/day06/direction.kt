package code.of.advent.day06

private const val DIRECTIONS = "^>v<"

/**
 * Auxiliary datatype used by [AreaMap].
 * @property dx difference in x-axis (columns)
 * @property dy difference in y-axis (rows)
 */
enum class Direction(val dx: Int, val dy: Int) {
    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

    fun rightTurn() =
        when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
}

fun Char.toDirection(): Direction =
    when (this) {
        '^' -> Direction.NORTH
        '>' -> Direction.EAST
        'v' -> Direction.SOUTH
        '<' -> Direction.WEST
        else -> error("Direction must be one of $DIRECTIONS but was $this")
    }
