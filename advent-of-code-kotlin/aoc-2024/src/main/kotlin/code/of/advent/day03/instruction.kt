/**
 * Auxiliary functions and/or datatypes
 * for [Day03] puzzle solution.
 */
package code.of.advent.day03

/**
 * Central datatype of the [Day03] solution.
 */
typealias Instruction = String

private const val OP_DO = "do()"
private const val OP_DONT = "don't()"
private val NUMBER_REGEX = "\\d+".toRegex()

/**
 * Calculates result of "mul" instruction.
 * (assumes it's only called on "mul")
 */
fun Instruction.evaluate(): Long {
    val (a, b) = NUMBER_REGEX.findAll(this).toList()
    return a.value.toLong() * b.value.toLong()
}

/**
 * State-less instruction filter. Assumes
 * valid instruction set (i.e. only do/don't/mul).
 */
fun mulFilter(instruction: Instruction): Boolean =
    when (instruction) {
        OP_DO, OP_DONT -> false
        else -> true
    }

/**
 * State-full instruction filter. Assumes
 * valid instruction set (i.e. only do/don't/mul).
 */
class OnOffFilter {
    private var enabled: Boolean = true
    operator fun invoke(instruction: Instruction): Boolean =
        when (instruction) {
            OP_DO -> { enabled = true; false }
            OP_DONT -> { enabled = false; false }
            else -> enabled
        }
}
