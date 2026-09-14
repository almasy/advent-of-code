package code.of.advent.logging

/** T.B.D. */
public class JulWrapperFactory : WrapperFactory {
    public override fun wrappedLogger(name: String): Logger {
        val julLogger = java.util.logging.Logger.getLogger(name)
        return JulWrapper(julLogger)
    }
}

/** T.B.D. */
public class JulWrapper(public val logger: java.util.logging.Logger) : Logger {
    public override val name: String
        get() = logger.name

    public override fun isEnabled(level: Level): Boolean = logger.isLoggable(level.toJul())

    public override fun at(level: Level, init: LoggingEventBuilder.() -> Unit) {
        val julLevel = level.toJul()
        if (!logger.isLoggable(julLevel))
            return
        val (sourceClass, sourceMethod) = lookUpSourceInStackTrace()
        val builder = LoggingEventBuilder().apply(init)
        logger.logp(julLevel, sourceClass, sourceMethod, builder.message, builder.cause)
    }

    private fun lookUpSourceInStackTrace(): Pair<String, String> {
        val stackTrace = Throwable().stackTrace
        val thisClass = this::class.java.name
        val frameIndex = stackTrace.indexOfLast { it.className == thisClass }
        val sourceFrame =
            if (frameIndex in 0..<stackTrace.lastIndex)
                stackTrace[frameIndex + 1]
            else                        // should not happen...
                stackTrace[1]           // however it's safe to assume parent caller on stack
        return sourceFrame.className to sourceFrame.methodName
    }

    private fun Level.toJul(): java.util.logging.Level =
        when(this) {
            Level.ERROR -> java.util.logging.Level.SEVERE
            Level.WARN -> java.util.logging.Level.WARNING
            Level.INFO -> java.util.logging.Level.INFO
            Level.DEBUG -> java.util.logging.Level.FINE
            Level.TRACE -> java.util.logging.Level.FINEST
            Level.OFF -> java.util.logging.Level.OFF
        }

}
