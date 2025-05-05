package code.of.advent.day05

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
class Day05InputTest : ShouldSpec({

    context("Day05Input.loadFrom") {

        should("throw exception for non-existent file").config(enabledIf = { Path(NON_EXISTENT_FILE).notExists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day05Input(IntegrationContext).loadFrom(NON_EXISTENT_FILE)
            }.cause.shouldBeInstanceOf<FileNotFoundException>()
        }

        should("throw exception for invalid puzzle format").config(enabledIf = { Path(GARBAGE_FILE).exists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day05Input(IntegrationContext).loadFrom(GARBAGE_FILE)
            }.cause.shouldBeInstanceOf<NumberFormatException>()
        }

        val badRule = "$INPUT_ROOT/day05-badrule.txt"
        should("throw exception for invalid rule").config(enabledIf = { Path(badRule).exists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day05Input(IntegrationContext).loadFrom(badRule)
            }.cause.shouldBeInstanceOf<IndexOutOfBoundsException>()
        }

        val badUpdate = "$INPUT_ROOT/day05-badupdate.txt"
        should("throw exception for empty page update").config(enabledIf = { Path(badUpdate).exists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day05Input(IntegrationContext).loadFrom(badUpdate)
            }.cause.shouldBeInstanceOf<NumberFormatException>()
        }
    }

})
