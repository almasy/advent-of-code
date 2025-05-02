package code.of.advent

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource
import kotlin.time.TimedValue

sealed class TickStepTiming(val step: Duration = 1.milliseconds) : PuzzleTiming {
    private var timeMark: TimeMark = TimeSource.Monotonic.markNow()
    var counter = 0L

    override fun <T> measureTimedValue(block: () -> T): TimedValue<T> {
        val start = now()
        val value = block()
        val stop = now()
        return TimedValue(value, stop - start)
    }

    override fun measureTime(block: () -> Unit): Duration {
        val start = now()
        block()
        return now() - start
    }

    override fun now(): TimeMark {
        counter++
        timeMark += step
        return timeMark
    }

}

object MilliTickTiming : TickStepTiming()
object SecondTickTiming : TickStepTiming(step = 1.seconds)
