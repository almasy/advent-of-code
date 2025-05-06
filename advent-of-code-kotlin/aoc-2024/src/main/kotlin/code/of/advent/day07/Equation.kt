package code.of.advent.day07

/**
 * Core logic using backtracking to find (any) combination
 * of operators (see [Operator]) which lead to a solution
 * of the equation.
 * @property result a number which we should get by applying operators
 * on [operands].
 * @property operands a list of numbers, which in combination with
 * operators should give [result].
 */
class Equation(val result: Long, private val operands: List<Long>) {

    fun hasSolution(operators: List<Operator>): Boolean {

        fun calculate(acc: Long, position: Int): Boolean {
            if (position == operands.size) {
                return acc == result
            }
            operators.forEach { operator ->
                if (calculate(operator(acc, operands[position]), position + 1)) {
                    return true
                }
            }
            return false
        }

        return calculate(operands[0], 1)
    }
}
