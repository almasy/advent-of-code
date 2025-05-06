/**
 * Auxiliary functions and/or datatypes
 * for [Day06] puzzle solution.
 */
package code.of.advent.day06

import code.of.advent.PuzzleInputException

/**
 * @property x horizontal position (i.e. column)
 * @property y vertical position (i.e. row)
 */
data class Position(val x: Int, val y: Int) {
    fun due(direction: Direction): Position =
        Position(x + direction.dx, y + direction.dy)
}

data class Guard(val position: Position, val direction: Direction)

/**
 * Central datatype of [Day06] solution. See [walkPositions]
 * and [hasLoopWith].
 */
class AreaMap(private val areaMap: List<List<Char>>) {
    private val sizeX: Int
    private val sizeY: Int
    val guard: Guard

    init {
        require(areaMap.isNotEmpty())
        require(areaMap.first().isNotEmpty())
        sizeX = areaMap.first().size
        sizeY = areaMap.size
        guard = findGuard()
    }

    fun obstacleAt(position: Position): Boolean =
        position in this && areaMap[position.y][position.x] == OBSTACLE

    operator fun contains(position: Position): Boolean =
        position.x in 0..<sizeX && position.y in 0..<sizeY

    /**
     * Traverses [guard] through the [areaMap] avoiding obstacles up until
     * the [guard] leaves [areaMap] boundaries.
     *
     * Note - this method does *not* check for cycles (hence will run forever,
     * when given "bad" areaMap)!
     */
    fun walkPositions(): MutableSet<Position> {
        var (position, direction) = guard
        val visited: MutableSet<Position> = mutableSetOf()
        while (position in this) {
            val newPosition = position.due(direction)
            if (obstacleAt(newPosition)) {
                direction = direction.rightTurn()
            } else {
                visited.add(position)
                position = newPosition
            }
        }
        return visited
    }

    /**
     * Traverses [guard] through the [areaMap] avoiding obstacles,
     * including a new one placed at [obstacle] position. Ends either
     * when the [guard] leaves the [areaMap] boundaries, or when
     * a cycle is detected.
     */
    fun hasLoopWith(obstacle: Position): Boolean {
        var (position, direction) = guard
        val visited = mutableSetOf<Guard>()
        while (position in this) {
            val current = Guard(position, direction)
            if (current in visited) {
                return true
            }
            visited.add(current)
            val newPosition = position.due(direction)
            if (newPosition == obstacle || obstacleAt(newPosition)) {
                direction = direction.rightTurn()
            } else {
                position = newPosition
            }
        }
        return false
    }

    private fun findGuard(): Guard {
        for ((y, row) in areaMap.withIndex()) {
            val item = row.withIndex().find { it.value in GUARDS }
            if (item != null) {
                return Guard(Position(item.index, y), item.value.toDirection())
            }
        }
        throw PuzzleInputException("No guard found on the map!")
    }

    private companion object {
        private const val OBSTACLE = '#'
        private const val GUARDS = "^>v<"
    }
}
