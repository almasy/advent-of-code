package code.of.advent.day06

import code.of.advent.*
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import java.io.FileNotFoundException
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.notExists

@Tags(INTEGRATION)
class Day06InputTest : ShouldSpec({

    context("Day06Input.loadFrom") {

        should("throw exception for non-existent file").config(enabledIf = { Path(NON_EXISTENT_FILE).notExists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day06Input(IntegrationContext).loadFrom(NON_EXISTENT_FILE)
            }.cause.shouldBeInstanceOf<FileNotFoundException>()
        }

        should("throw exception for empty file").config(enabledIf = { Path(EMPTY_FILE).exists() }) {
            shouldThrowExactly<PuzzleFormatException> {
                Day06Input(IntegrationContext).loadFrom(EMPTY_FILE)
            }.cause.shouldBeInstanceOf<IllegalArgumentException>()
        }

        val emptyLine = "$INPUT_ROOT/day06-empty.txt"
        should("throw exception for puzzle with empty line").config(enabledIf = { Path(emptyLine).exists() }) {
            shouldThrowExactly<PuzzleFormatException> {
                Day06Input(IntegrationContext).loadFrom(emptyLine)
            }.cause.shouldBeInstanceOf<IllegalArgumentException>()
        }

        should("throw exception for puzzle without guard").config(enabledIf = { Path(NO_GUARD).exists() }) {
            shouldThrowExactly<PuzzleContentsException> {
                Day06Input(IntegrationContext).loadFrom(NO_GUARD)
            }.cause.shouldBeNull()
        }

    }
})
