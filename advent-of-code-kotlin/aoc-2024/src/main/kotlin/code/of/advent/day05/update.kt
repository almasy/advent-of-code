/**
 * Auxiliary functions and/or datatypes
 * for [Day05] puzzle solution.
 */
package code.of.advent.day05

/**
 * Central datatype of the [Day05] solution.
 * See also [isCorrect] and [toFixedUpdate].
 */
typealias Update = List<Int>

/**
 * Verifies, whether this [Update] conforms to all [rules]
 */
fun Update.isCorrect(rules: RuleMap): Boolean {
    for ((index, page) in this.withIndex()) {
        if (page !in rules) continue
        val pageRules = rules.getValue(page)
        if (take(index).any { it in pageRules }) return false
    }
    return true
}

/**
 * Creates a copy of this [Update] and adjusts the page
 * order so, that it conforms to all [rules].
 */
fun Update.toFixedUpdate(rules: RuleMap): Update {
    val orderedUpdate = this.toMutableList()
    var ids = orderedUpdate.violationIds(rules)
    while (ids.first != ids.second) {
        val temp = orderedUpdate[ids.first]
        orderedUpdate[ids.first] = orderedUpdate[ids.second]
        orderedUpdate[ids.second] = temp
        ids = orderedUpdate.violationIds(rules)
    }
    return orderedUpdate
}

private fun Update.violationIds(rules: RuleMap): Pair<Int, Int> {
    for ((second, page) in this.withIndex()) {
        if (page !in rules) continue
        val pageRules = rules.getValue(page)
        take(second).withIndex()
            .find { first -> first.value in pageRules }
            ?.let { first -> return first.index to second }
    }
    return 0 to 0
}
