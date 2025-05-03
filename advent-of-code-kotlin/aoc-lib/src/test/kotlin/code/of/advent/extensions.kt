package code.of.advent

import io.kotest.extensions.system.captureStandardOut
import java.io.ByteArrayOutputStream
import java.io.PrintStream

/**
 * Similar to Kotest's [captureStandardOut], but instead
 * of the captured input, it returns the result of the called
 * [block].
 */
inline fun <T> suppressStandardOut(block: () -> T): T {
    val previous = System.out
    System.out.flush()
    System.setOut(PrintStream(ByteArrayOutputStream()))
    try {
        return block()
    } finally {
        System.setOut(previous)
    }
}
