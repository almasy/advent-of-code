package code.of.advent

import io.kotest.core.Tag

const val INPUT_ROOT = "./src/test/resources/inputs"
const val NON_EXISTENT_FILE = "$INPUT_ROOT/does_not_exist.txt"
const val GARBAGE_FILE = "$INPUT_ROOT/garbage.txt"
const val EMPTY_FILE = "$INPUT_ROOT/empty.txt"

val IntegrationContext = DefaultContext

object Integration : Tag()
const val INTEGRATION = "Integration"
