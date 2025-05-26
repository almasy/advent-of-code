package code.of.advent

const val TEST_DAY01_INPUT_LOAD = 123

sealed class InputTestBase : PuzzleInput<Int> {
    override val source = "./does/not/matter"
    override fun loadFrom(fileName: String) = TEST_DAY01_INPUT_LOAD
}

class TestDay01Input : InputTestBase()
class TestDay02Input : InputTestBase()
class TestDay99Input : InputTestBase()

class TestDayInput : InputTestBase()
class TestDayD01Input : InputTestBase()
class Test20DayInput : InputTestBase()

class FailingTest01Input : InputTestBase() {
    override fun loadFrom(fileName: String) =
        throw PuzzleFormatException("just a test")
}

class TestInputExample : InputTestBase()

val objectDay01Input = object : PuzzleInput<Int> {
    override val source = "./does/not/matter"
    override fun loadFrom(fileName: String): Int = source.length
}
