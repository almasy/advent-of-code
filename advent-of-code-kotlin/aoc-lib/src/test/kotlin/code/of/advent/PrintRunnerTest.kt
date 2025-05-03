package code.of.advent

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class PrintRunnerTest : ShouldSpec({

    context("PrintRunner.runWith") {
        val runner = PrintRunner(TestDay01Input(), TestDay01(), MilliTickTiming)

        should("run puzzle in DEFAULT mode") {
            val actual = suppressStandardOut { runner.runWith(DefaultRunContext) }
            actual.shouldBeSuccess()
            actual.onSuccess { puzzleResult ->
                assertSoftly(puzzleResult) {
                    input.duration shouldBeEqual Duration.ZERO
                    input.loops shouldBeEqual 1

                    part1.value shouldBeEqual TEST_DAY01_PART1
                    part1.duration shouldBeEqual Duration.ZERO
                    part1.loops shouldBeEqual 1

                    part2.value shouldBeEqual TEST_DAY01_PART2
                    part2.duration shouldBeEqual Duration.ZERO
                    part2.loops shouldBeEqual 1

                    total shouldBeEqual Duration.ZERO
                }
            }
        }

        should("run puzzle in MEASURE mode") {
            val actual = suppressStandardOut { runner.runWith(MeasureRunContext) }
            actual.shouldBeSuccess()
            actual.onSuccess { puzzleResult ->
                assertSoftly(puzzleResult) {
                    input.duration shouldBeEqual 1.milliseconds
                    input.loops shouldBeEqual 1

                    part1.value shouldBeEqual TEST_DAY01_PART1
                    part1.duration shouldBeEqual 1.milliseconds
                    part1.loops shouldBeEqual 1

                    part2.value shouldBeEqual TEST_DAY01_PART2
                    part2.duration shouldBeEqual 1.milliseconds
                    part2.loops shouldBeEqual 1

                    total shouldBeEqual (1 + 1 + 1).milliseconds
                }
            }
        }

        should("run puzzle in BENCHMARK mode") {
            val expectedTotal = (CUSTOM_LOOPS * 9 - 2).milliseconds
            val actual = suppressStandardOut { runner.runWith(BenchmarkRunContext) }
            actual.shouldBeSuccess()
            actual.onSuccess { puzzleResult ->
                assertSoftly(puzzleResult) {
                    input.duration shouldBeEqual 1.milliseconds
                    input.loops shouldBeEqual CUSTOM_LOOPS

                    part1.value shouldBeEqual TEST_DAY01_PART1
                    part1.duration shouldBeEqual 1.milliseconds
                    part1.loops shouldBeEqual CUSTOM_LOOPS

                    part2.value shouldBeEqual TEST_DAY01_PART2
                    part2.duration shouldBeEqual 1.milliseconds
                    part2.loops shouldBeEqual CUSTOM_LOOPS

                    total shouldBeEqual expectedTotal
                }
            }
        }
    }

    context("PrintRunner.runWith given long-running benchmark") {
        val runner = PrintRunner(TestDay01Input(), TestDay01(), SecondTickTiming)

        should("terminate benchmark after timeout") {
            val actual = suppressStandardOut { runner.runWith(BenchmarkRunContext) }
            actual.shouldBeSuccess()
            actual.onSuccess { puzzleResult ->
                assertSoftly(puzzleResult) {
                    input.loops shouldBeEqual CUSTOM_LOOPS
                    part1.loops shouldBeEqual CUSTOM_LOOPS
                    part2.loops shouldBeEqual 1

                    total shouldBeGreaterThan CUSTOM_DURATION
                }
            }
        }

        should("run each part at least once") {
            val actual = suppressStandardOut { runner.runWith(HighLoopsContext) }
            actual.shouldBeSuccess()
            actual.onSuccess { puzzleResult ->
                assertSoftly(puzzleResult) {
                    input.loops shouldBeEqual 20    // 60s with 3 ticks per loop => 20 loops
                    part1.loops shouldBeEqual 1
                    part2.loops shouldBeEqual 1

                    total shouldBeGreaterThan CUSTOM_DURATION
                }
            }
        }
    }

    context("PrintRunner.runWith given failing") {
        withData(
            nameFn = { " ${it.third} should return Result indicating failure" },
            Triple(FailingTest01Input(), TestDay01(), "puzzle input"),
            Triple(TestDay02Input(), FailingDay02(), "puzzle solution"),
        ) { (input, puzzle, _) ->
        val runner = PrintRunner(input, puzzle)
            suppressStandardOut { runner.runWith(BenchmarkRunContext) }.shouldBeFailure()
        }
    }

    context("PrintRunner.runWith given invalid context") {
        val runner = PrintRunner(TestDay01Input(), TestDay01())
        should("return Result indicating failure") {
            suppressStandardOut { runner.runWith(InvalidContext) }.shouldBeFailure()
        }
    }

})
