/**
 * Auxiliary functions and/or datatypes
 * for [Day02] puzzle solution.
 */
package code.of.advent.day02

/**
 * Central datatype of the [Day02] solution.
 * See also [safe] and [safeWithDampener].
 */
typealias Report = List<Int>

/**
 * Note - [safe] assumes valid input - a list
 * of integers containing at least 2 items.
 */
fun Report.safe(): Boolean {
    val deltas = this.zipWithNext { a, b -> a - b }
    val direction = if (deltas.first() < 0) -1 else 1
    return deltas.all { it * direction in 1..3  }
}

/**
 * Note - [safeWithDampener] assumes valid input - a list
 * of integers containing at least 2 items.
 */
fun Report.safeWithDampener(): Boolean = subReports().any { it.safe() }

private fun Report.subReports(): Sequence<Report> = sequence {
    val report = this@subReports
    yield(report)
    report.indices.forEach {
        yield(report.subList(0, it) + report.subList(it + 1, report.size))
    }
}
