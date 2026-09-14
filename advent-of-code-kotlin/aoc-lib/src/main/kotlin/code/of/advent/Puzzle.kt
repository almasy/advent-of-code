package code.of.advent

/**
 * Common template for AOC daily puzzle solutions.
 *
 * The [T] specifies the type of (loaded) puzzle input
 * (see [PuzzleInput]). The [R] denotes the type
 * of result returned by both [part1] and [part2] solution
 * implementation.
 * @property year holds AoC year (e.g. "2024")
 * @property day holds a name of AoC day (e.g. "Day 1")
 */
public interface Puzzle<T , R> {
    public val year: String
    public val day: String

    /**
     * Solution of part of the AOC daily puzzle.
     * For details on [input] see [PuzzleInput].
     */
    public fun part1(input: T): R

    /**
     * Solution of part2 of the AOC daily puzzle.
     * For details on [input] see [PuzzleInput].
     */
    public fun part2(input: T): R

    private fun dayFromClass(): String =
        this::class.simpleName?.let { name ->
            "Day(\\d{1,2})".toRegex().find(name)?.value?.lowercase()
        } ?: throw IllegalStateException("Can't parse day from: ${this::class.simpleName}")

}
