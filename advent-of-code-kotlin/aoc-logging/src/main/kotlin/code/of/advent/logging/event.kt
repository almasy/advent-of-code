package code.of.advent.logging

/** T.B.D. */
public class LoggingEventBuilder(
    public var message: String? = null,
    public var cause: Throwable? = null,
    public var marker: Marker? = null
)

/** T.B.D. */
public class LoggingEvent(
    public val level: Level,
    public val loggerName: String,
    public val message: String? = null,
    public val cause: Throwable? = null,
    public val marker: Marker? = null
)
