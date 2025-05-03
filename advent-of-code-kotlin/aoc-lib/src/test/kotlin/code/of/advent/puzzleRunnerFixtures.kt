package code.of.advent

const val TEST_DAY01_PART1 = 1230 // input = 123, part1 = input * 10
const val TEST_DAY01_PART2 = 2460 // input = 123, part1 = input * 20

class TestDay01 : Puzzle<Int, Int> {
    override val year = "2002"
    override val day = "day01"
    override fun part1(input: Int) = input * 10
    override fun part2(input: Int) = input * 20
}

class FailingDay02 : Puzzle<Int, Int> {
    override val year = "2002"
    override val day = "day02"
    override fun part1(input: Int): Int =
        throw NullPointerException("just a test")

    override fun part2(input: Int): Int = input / 2
}
