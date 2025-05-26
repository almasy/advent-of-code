package code.of.advent.day08

import code.of.advent.*
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import java.io.FileNotFoundException
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.notExists

@Tags(INTEGRATION)
class Day08InputTest : ShouldSpec({

    context("Day08Input.loadFrom") {
        should("throw exception for non-existent file").config(enabledIf = { Path(NON_EXISTENT_FILE).notExists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day08Input(IntegrationContext).loadFrom(NON_EXISTENT_FILE)
            }.cause.shouldBeInstanceOf<FileNotFoundException>()
        }

        should("throw exception for empty file").config(enabledIf = { Path(EMPTY_FILE).exists() }) {
            shouldThrowExactly<PuzzleFormatException> {
                Day08Input(IntegrationContext).loadFrom(EMPTY_FILE)
            }.cause.shouldBeInstanceOf<IllegalArgumentException>()
        }

        val emptyLine = "$INPUT_ROOT/day08-empty.txt"
        should("throw exception for empty file").config(enabledIf = { Path(emptyLine).exists() }) {
            shouldThrowExactly<PuzzleFormatException> {
                Day08Input(IntegrationContext).loadFrom(emptyLine)
            }.cause.shouldBeInstanceOf<IllegalArgumentException>()
        }

    }

})
