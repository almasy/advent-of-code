package code.of.advent.day03

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
import kotlin.io.path.notExists

@Tags(INTEGRATION)
class Day03InputTest : ShouldSpec({

    context("Day03Input.loadFrom") {
        should("throw exception for non-existent file").config(enabledIf = { Path(NON_EXISTENT_FILE).notExists() }) {
            shouldThrowExactly<PuzzleInputException> {
                Day03Input(IntegrationContext).loadFrom(NON_EXISTENT_FILE)
            }.cause.shouldBeInstanceOf<FileNotFoundException>()
        }
    }

})
