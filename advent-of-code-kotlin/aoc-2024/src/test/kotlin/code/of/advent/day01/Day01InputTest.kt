package code.of.advent.day01

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
class Day01InputTest : ShouldSpec({

    context("Day01Input.loadFrom") {

        should("throw exception for non-existent file").config(enabledIf = { Path(NON_EXISTENT_FILE).notExists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day01Input(IntegrationContext).loadFrom(NON_EXISTENT_FILE)
            }.cause.shouldBeInstanceOf<FileNotFoundException>()
        }

        should("throw exception for invalid puzzle format").config(enabledIf = { Path(GARBAGE_FILE).exists() }) {
            shouldThrowExactly<PuzzleFormatException> {
                Day01Input(IntegrationContext).loadFrom(GARBAGE_FILE)
            }.cause.shouldBeInstanceOf<NumberFormatException>()
        }
    }

})
