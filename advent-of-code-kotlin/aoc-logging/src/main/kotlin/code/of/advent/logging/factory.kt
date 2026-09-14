package code.of.advent.logging


/** T.B.D. */
public object LoggerFactory {
    /** T.B.D. */
    private val wrapperFactory = JulWrapperFactory()

    /** T.B.D. */
    public fun logger(name: String): Logger = resolveLogger(name)
    /** T.B.D. */
    public fun logger(lambda: () -> Unit): Logger = resolveLogger(resolveName(lambda))

    private fun resolveName(lambda: () -> Unit): String =
        lambda::class.java.name.substringBefore("$$")

    private fun resolveLogger(name: String): Logger = wrapperFactory.wrappedLogger(name)

}

/** T.B.D. */
public interface WrapperFactory {
    public fun wrappedLogger(name: String): Logger
}
