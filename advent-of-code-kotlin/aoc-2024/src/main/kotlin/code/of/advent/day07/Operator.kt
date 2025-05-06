package code.of.advent.day07

/**
 * Auxiliary datatype used by [Equation] and [Day07].
 */
enum class Operator {
    ADD {
        override operator fun invoke(a: Long, b: Long): Long = a + b
    },
    MUL {
        override operator fun invoke(a: Long, b: Long): Long = a * b
    },
    JOIN {
        override operator fun invoke(a: Long, b: Long): Long = "$a$b".toLong()
    };

    abstract operator fun invoke(a: Long, b: Long): Long
}
