package code.of.advent.logging

/** T.B.D. */
public interface Logger {
    /** Logger name */
    public val name: String

    /** T.B.D. */
    public fun error(message: () -> Any?): Unit = error(null, message)
    /** T.B.D. */
    public fun error(cause: Throwable?, message: () -> Any?): Unit =
        atError {
            this.cause = cause
            this.message = message()?.toString()
        }
    /** T.B.D. */
    public fun warn(message: () -> Any?): Unit = warn(null, message)
    /** T.B.D. */
    public fun warn(cause: Throwable?, message: () -> Any?): Unit =
        atWarn {
            this.cause = cause
            this.message = message()?.toString()
        }
    /** T.B.D. */
    public fun info(message: () -> Any?): Unit = info(null, message)
    /** T.B.D. */
    public fun info(cause: Throwable?, message: () -> Any?): Unit =
        atInfo {
            this.cause = cause
            this.message = message()?.toString()
        }
    /** T.B.D. */
    public fun debug(message: () -> Any?): Unit = debug(null, message)
    /** T.B.D. */
    public fun debug(cause: Throwable?, message: () -> Any?): Unit =
        atDebug {
            this.cause = cause
            this.message = message()?.toString()
        }
    /** T.B.D. */
    public fun trace(message: () -> Any?): Unit = trace(null, message)
    /** T.B.D. */
    public fun trace(cause: Throwable?, message: () -> Any?): Unit =
        atTrace {
            this.cause = cause
            this.message = message()?.toString()
        }

    /** T.B.D. */
    public fun at(level: Level, init: LoggingEventBuilder.() -> Unit)
    /** T.B.D. */
    public fun atError(init: LoggingEventBuilder.() -> Unit): Unit = at(Level.ERROR, init)
    /** T.B.D. */
    public fun atWarn(init: LoggingEventBuilder.() -> Unit): Unit = at(Level.WARN, init)
    /** T.B.D. */
    public fun atInfo(init: LoggingEventBuilder.() -> Unit): Unit = at(Level.INFO, init)
    /** T.B.D. */
    public fun atDebug(init: LoggingEventBuilder.() -> Unit): Unit = at(Level.DEBUG, init)
    /** T.B.D. */
    public fun atTrace(init: LoggingEventBuilder.() -> Unit): Unit = at(Level.TRACE, init)

    /** T.B.D. */
    public fun isEnabled(level: Level): Boolean
}

/**
 * T.B.D.
 */
public class Marker(public val name: String)
