package code.of.advent.day06

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class DirectionTest : ShouldSpec({

    context("toDirection") {
        withData(
            nameFn = {"converts '${it.first}' to ${it.second}" },
            '^' to Direction.NORTH,
            '>' to Direction.EAST,
            'v' to Direction.SOUTH,
            '<' to Direction.WEST,
        ) { (charValue, enumValue) ->
            charValue.toDirection() shouldBe enumValue
        }

        withData(
            nameFn = {"throws an exception for other characters"},
            'a', 'b', '-', '#'
        ) { char ->
            shouldThrowExactly<IllegalStateException> { char.toDirection() }
        }
    }

})
