package code.of.advent.day04

import code.of.advent.EMPTY_FILE
import code.of.advent.INPUT_ROOT
import code.of.advent.INTEGRATION
import code.of.advent.IntegrationContext
import code.of.advent.NON_EXISTENT_FILE
import code.of.advent.PuzzleInputException
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import java.io.FileNotFoundException
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.notExists

@Tags(INTEGRATION)
class Day04InputTest : ShouldSpec({

    context("Day04Input.loadFrom") {
        should("throw exception for non-existent file").config(enabledIf = { Path(NON_EXISTENT_FILE).notExists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day04Input(IntegrationContext).loadFrom(NON_EXISTENT_FILE)
            }.cause.shouldBeInstanceOf<FileNotFoundException>()
        }

        should("throw exception for empty file").config(enabledIf = { Path(EMPTY_FILE).exists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day04Input(IntegrationContext).loadFrom(EMPTY_FILE)
            }.cause.shouldBeInstanceOf<IllegalArgumentException>()
        }

        val emptyLine = "$INPUT_ROOT/day04-empty.txt"
        should("throw exception for puzzle with empty line").config(enabledIf = { Path(emptyLine).exists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day04Input(IntegrationContext).loadFrom(emptyLine)
            }.cause.shouldBeInstanceOf<IllegalArgumentException>()
        }
    }

})
