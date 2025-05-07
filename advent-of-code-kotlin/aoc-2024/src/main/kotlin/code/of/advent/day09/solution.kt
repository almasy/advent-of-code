/**
 * Advent of Code 2024, day 9
 * https://adventofcode.com/2024/day/9
 */
package code.of.advent.day09

import code.of.advent.*

private const val EMPTY_BLOCK_ID = -1

/**
 * Representation of the core datatype for [Day09] solution.
 * @property size amount of "unpacked/raw" blocks
 * @property id either a file ID or an empty block
 */
data class PackedBlock(val size: Int, val id: Int = EMPTY_BLOCK_ID) {
    val isEmpty: Boolean = id == EMPTY_BLOCK_ID

    fun unpack(): List<Int> = buildList {
        repeat(this@PackedBlock.size) { add(id) }
    }
}

/**
 * Loader and parser of input data for [Day09] puzzle.
 * See [PuzzleRunner], [Puzzle] & [PuzzleInput].
 */
class Day09Input(context: Context): PuzzleInput<MutableList<PackedBlock>> {
    override val source: String = buildSourceWith(context)

    override fun loadFrom(fileName: String): MutableList<PackedBlock> =
        runWithFile(fileName) { file ->
            val result = mutableListOf<PackedBlock>()
            file.bufferedReader().use { reader ->
                reader.readText().chunked(2).forEachIndexed { idx, chunk ->
                    val fileSize = chunk[0].digitToInt()
                    val emptySize = chunk.getOrNull(1)?.digitToInt() ?: 0
                    result.add(PackedBlock(size = fileSize, id = idx))
                    if (emptySize > 0)
                        result.add(PackedBlock(size = emptySize))
                }
            }
            return result
        }
}

/**
 * Implementation of
 * [Advent of Code 2024 Day 9](https://adventofcode.com/2024/day/9)
 * puzzle.
 */
class Day09: Puzzle<MutableList<PackedBlock>, Long> {
    override val year = "2024"
    override val day = "Day 9"

    /**
     * Moves as much of individual "file" blocks from the end
     * of the "disk" to "empty" blocks at the beginning of the
     * "disk" as possible.
     * @return [checksum] of the "defragmented" "disk"
     */
    override fun part1(input: MutableList<PackedBlock>): Long {
        val unpackedDisk = input.unpack()
        var targetIdx = 0
        var sourceIdx = unpackedDisk.lastIndex
        while (targetIdx < sourceIdx) {
            if (unpackedDisk[targetIdx] != EMPTY_BLOCK_ID) {
                targetIdx++
                continue
            }
            if (unpackedDisk[sourceIdx] == EMPTY_BLOCK_ID) {
                sourceIdx--
                continue
            }
            unpackedDisk[targetIdx++] = unpackedDisk[sourceIdx]
            unpackedDisk[sourceIdx--] = EMPTY_BLOCK_ID
        }
        return unpackedDisk.checksum()
    }

    /**
     * Moves as much of the whole files (i.e. all of their blocks) from
     * the end of the "disk" to "empty" blocks at the beginning of the
     * "disk" as possible.
     * @return [checksum] of the "defragmented" "disk"
     */
    override fun part2(input: MutableList<PackedBlock>): Long {
        var sourceIdx = input.lastIndex
        while (sourceIdx > 0) {
            while (sourceIdx > 0 && input[sourceIdx].isEmpty) {
                sourceIdx--
            }
            val source = input[sourceIdx]
            var targetIdx = 0
            var target = input[targetIdx]
            while (targetIdx < sourceIdx && !target.isEmpty || target.size < source.size) {
                targetIdx++
                target = input[targetIdx]
            }
            // swap only to positions before current (source) one
            if (targetIdx < sourceIdx) {
                input[sourceIdx] = PackedBlock(source.size)
                input[targetIdx] = source
                val newTargetSize = target.size - source.size
                if (newTargetSize == 0) {
                    sourceIdx--
                } else {
                    input.add(targetIdx + 1, PackedBlock(newTargetSize))
                }
            } else {
                sourceIdx--
            }
        }
        return input.unpack().checksum()
    }

    private fun MutableList<PackedBlock>.unpack(): MutableList<Int> =
        this.flatMap { it.unpack() } .toMutableList()

    /**
     * Calculates "checksum" as specified by Day 9 requirements / story.
     */
    private fun List<Int>.checksum(): Long =
        this.withIndex()
            .filter { it.value != EMPTY_BLOCK_ID }
            .sumOf { it.index * it.value.toLong() }
}

fun main() {
    PrintRunner(Day09Input(DefaultContext), Day09()).runWith(DefaultContext)
}
