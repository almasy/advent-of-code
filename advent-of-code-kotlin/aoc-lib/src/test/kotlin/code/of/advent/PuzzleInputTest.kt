package code.of.advent

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.io.FileNotFoundException
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

class PuzzleInputTest : ShouldSpec({

    context("PuzzleInput.load") {
        val dummyInput = object : PuzzleInput<Int> {
            override val source = "./does/not/matter"
            override fun loadFrom(fileName: String) = 12345
        }

        should("use loadFrom to load puzzle input") {
            dummyInput.load() shouldBeExactly 12345
        }
    }

    context("PuzzleInput.day") {
        withData(
            nameFn = { "for class ${it.first::class.simpleName} should be ${it.second}" },
            TestDay01Input() to "day01",
            TestDay02Input() to "day02",
            TestDay99Input() to "day99",
        ) { (input, day) ->
            input.day shouldBe day
        }

        withData(
            nameFn = { "for class ${it::class.simpleName} should throw exception" },
            TestDayInput(),
            TestDayD01Input(),
            Test20DayInput(),
            TestInputExample(),
            objectDay01Input,
        ) { input ->
            shouldThrowExactly<IllegalStateException> { input.day }
                .message shouldBe "Can't parse day from: ${input::class.simpleName}"
        }
    }

    context("PuzzleInput.buildSourceWith") {
        val puzzleContext = PropertyContext.from(
            propertiesOf(
                inputRoot = "./aoc/inputs/dummy/path/../../",
                inputSession = "",
                runMode = "DEFAULT",
                warmUp = "0",
                loops = "2",
                limit = "1")
        )
        should("should build full path to puzzle input source") {
            // need to use Path to ensure compatibility across operating systems...
            val expected = Path("./aoc/inputs", "day01.txt").normalize().absolutePathString()
            val actual = TestDay01Input().buildSourceWith(puzzleContext)
            actual shouldBe expected
        }
    }

    context("runWithFile") {
        withData(
            nameFn = { "should convert ${it::class.simpleName} to PuzzleInputException" },
            FileNotFoundException(),
            NumberFormatException(),
            IndexOutOfBoundsException(),
            IllegalArgumentException(),
        ) { exception ->
            shouldThrowExactly<PuzzleInputException> {
                runWithFile("./does/not/matter.txt") { throw exception }
            }.cause.shouldBeSameInstanceAs(exception)
        }

        withData(
            nameFn = { "should rethrow ${it::class.simpleName}" },
            ArithmeticException(),
            ClassCastException(),
            OutOfMemoryError(),
        ) { exception ->
            shouldThrowAny {
                runWithFile("./does/not/matter.txt") { throw exception }
            }.shouldBeSameInstanceAs(exception)
        }
    }

})
