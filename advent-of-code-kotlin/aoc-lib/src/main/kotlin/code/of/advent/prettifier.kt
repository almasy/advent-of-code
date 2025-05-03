package code.of.advent

import kotlin.time.Duration
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

/**
 * Function performs a custom "pretty formatting"
 * of given [Duration] instance following few basic
 * rules
 * - Zero components are not included
 * - Durations over a day only include days, hours and minutes
 * - Durations under a day but over one hour include hours, minutes and seconds
 * - Durations under one hour but over one minute include minutes and seconds
 * - Durations under one minute but over one second include seconds and milliseconds
 * - Durations under one second but over one microsecond include millis and micros (as fraction)
 * - Durations under one microsecond are simply shown as nanoseconds
 */
fun Duration.toPrettyString(): String =
    absRound().toComponents { days, hours, minutes, seconds, nanos ->
        listOf(
            days withUnit "d",
            hours withUnit "h",
            minutes withUnit "m",
            seconds withUnit "s",
            (nanos / 1000L) / 1000.0 withUnit "ms",
            nanos % 1000L withUnit "ns"
        )
        .filter { it.first > 0.0 }
        .joinToString(" ") { it.second }
        .let { if (it.isEmpty()) "0ns" else "${sign()}$it" }
    }

private fun Duration.sign() = if (isNegative()) "-" else ""

/**
 * Chooses a unit for [Duration] rounding based on
 * its value and then performs the rounding.
 * Returns **absolute value**
 */
private fun Duration.absRound(): Duration {
    var duration = absoluteValue
    if (duration.inWholeNanoseconds < 1_000) return duration
    val unit = when {
        duration.inWholeSeconds == 0L -> DurationUnit.NANOSECONDS
        duration.inWholeMinutes == 0L -> DurationUnit.MICROSECONDS
        duration.inWholeHours == 0L -> DurationUnit.MILLISECONDS
        duration.inWholeDays == 0L -> DurationUnit.MILLISECONDS
        else -> DurationUnit.SECONDS
    }
    return duration.absRound(unit)
}

/**
 * Performs [Duration] rounding on the given [unit]
 * level up to the next (higher) unit level.
 */
private fun Duration.absRound(unit: DurationUnit): Duration =
    when (unit) {
        DurationUnit.NANOSECONDS -> {
            val rounded = this + 500.nanoseconds
            rounded.inWholeMicroseconds.microseconds
        }
        DurationUnit.MICROSECONDS -> {
            val rounded = this + 500.microseconds
            rounded.inWholeMilliseconds.milliseconds
        }
        DurationUnit.MILLISECONDS -> {
            val rounded = this + 500.milliseconds
            rounded.inWholeSeconds.seconds
        }
        else -> {
            val rounded = this + 30.seconds
            rounded.inWholeMinutes.minutes
        }
    }

/**
 * Helper making the conversion "nicer to read"
 */
private infix fun Int.withUnit(unit: String) = toDouble() to "$this$unit"

/**
 * Helper making the conversion "nicer to read"
 */
private infix fun Long.withUnit(unit: String) = toDouble() to "$this$unit"

/**
 * Helper dropping decimal part of double value if it is 0
 * (i.e. 1.0 will be formatted as 1)
 */
private infix fun Double.withUnit(unit: String): Pair<Double, String> {
    val longValue = this.toLong()
    val formatted = if (longValue - this == 0.0) {
        longValue.toString()
    } else {
        toString()
    }
    return this to "$formatted$unit"
}
