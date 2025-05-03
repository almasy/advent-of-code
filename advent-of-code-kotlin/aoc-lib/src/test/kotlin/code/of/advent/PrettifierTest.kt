package code.of.advent

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

class PrettifierTest : ShouldSpec ({

    context("Given Duration.ZERO, Duration.toPrettyString") {
        should("return 0ns") {
            Duration.ZERO.toPrettyString() shouldBe "0ns"
        }
    }

    context("Duration.toPrettyString skips zero components") {
        withData(
            nameFn = { "\"${it.first}\" is rendered as \"${it.second}\"" },
            7.days to "7d",
            23.hours to "23h",
            15.minutes to "15m",
            30.seconds to "30s",
            987.milliseconds to "987ms",
            543.microseconds to "0.543ms",
            456.nanoseconds to "456ns",
            2.days + 12.hours to "2d 12h",
            1.days + 30.minutes to "1d 30m",
            1.hours + 1.seconds to "1h 1s",
            1.minutes + 1.seconds to "1m 1s",
        ) { (given, expected) ->
            given.toPrettyString() shouldBe expected
        }
    }

    context("Duration.toPrettyString does proper seconds rounding") {
        withData(
            nameFn = { "\"${it.first}\" is rendered as \"${it.second}\"" },
            1.days + 1.hours + 1.minutes + 1.seconds to "1d 1h 1m",
            1.days + 1.hours + 1.minutes + 29.seconds to "1d 1h 1m",
            1.days + 1.hours + 1.minutes + 30.seconds to "1d 1h 2m",
            1.days + 1.hours + 59.minutes + 31.seconds to "1d 2h",
            1.days + 23.hours + 59.minutes + 29.seconds to "1d 23h 59m",
            1.days + 23.hours + 59.minutes + 30.seconds to "2d",
        ) { (given, expected) ->
            given.toPrettyString() shouldBe expected
        }
    }

    context("Duration.toPrettyString does proper milliseconds rounding") {
        withData(
            nameFn = { "\"${it.first}\" is rendered as \"${it.second}\"" },
            1.minutes + 1.seconds + 100.milliseconds to "1m 1s",
            1.minutes + 1.seconds + 500.milliseconds to "1m 2s",
            1.minutes + 59.seconds + 500.milliseconds to "2m",
            0.minutes + 59.seconds + 500.milliseconds to "59s 500ms"
        ) { (given, expected) ->
            given.toPrettyString() shouldBe expected
        }
    }

    context("Duration.toPrettyString does proper microseconds rounding") {
        withData(
            nameFn = { "\"${it.first}\" is rendered as \"${it.second}\"" },
            1.seconds + 100.milliseconds + 100.microseconds to "1s 100ms",
            1.seconds + 500.milliseconds + 500.microseconds to "1s 501ms",
            59.seconds + 999.milliseconds + 499.microseconds to "59s 999ms",
            59.seconds + 999.milliseconds + 500.microseconds to "1m",
        ) { (given, expected) ->
            given.toPrettyString() shouldBe expected
        }
    }

    context("Duration.toPrettyString does proper nanoseconds rounding") {
        withData(
            nameFn = { "\"${it.first}\" is rendered as \"${it.second}\"" },
            5.milliseconds + 100.microseconds + 100.nanoseconds to "5.1ms",
            5.milliseconds + 500.microseconds + 500.nanoseconds to "5.501ms",
            1.milliseconds + 999.microseconds + 499.nanoseconds to "1.999ms",
            1.milliseconds + 999.microseconds + 500.nanoseconds to "2ms",
        ) { (given, expected) ->
            given.toPrettyString() shouldBe expected
        }
    }

    context("Given negative durations, Duration.toPrettyString adds \"-\" sign") {
        withData(
            nameFn = { "\"${it.first}\" is rendered as \"${it.second}\"" },
            (-1).days - 5.hours - 30.minutes to "-1d 5h 30m",
            (-5).hours - 30.minutes - 15.seconds to "-5h 30m 15s",
            (-2).minutes - 30.seconds - 250.milliseconds to "-2m 30s",
            (-5).seconds - 350.milliseconds to "-5s 350ms",
            (-10).milliseconds - 350.microseconds to "-10.35ms",
            (-10).microseconds - 350.nanoseconds to "-0.01ms",
            (-5).nanoseconds to "-5ns",
        ) { (given, expected) ->
            given.toPrettyString() shouldBe expected
        }
    }

})
