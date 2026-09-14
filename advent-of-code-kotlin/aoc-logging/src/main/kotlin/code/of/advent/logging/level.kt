package code.of.advent.logging

/** T.B.D. */
public enum class Level(private val value: Int) {
    ERROR(LevelValues.ERROR),
    WARN(LevelValues.WARN),
    INFO(LevelValues.INFO),
    DEBUG(LevelValues.DEBUG),
    TRACE(LevelValues.TRACE),
    OFF(LevelValues.OFF);

    /** T.B.D. */
    public fun toInt(): Int = value
}

/** T.B.D. */
internal object LevelValues {
    const val ERROR = 100
    const val WARN = 200
    const val INFO = 300
    const val DEBUG = 400
    const val TRACE = 500
    const val OFF = 0
    const val ALL = Int.MAX_VALUE
}

/** T.B.D. */
public fun Int.toLevel(): Level =
    when(this) {
        LevelValues.ERROR -> Level.ERROR
        LevelValues.WARN -> Level.WARN
        LevelValues.INFO -> Level.INFO
        LevelValues.DEBUG -> Level.DEBUG
        LevelValues.TRACE -> Level.TRACE
        LevelValues.OFF -> Level.OFF
        else -> error("Value $this isn't a recognized Level.")
    }
