package net.dslab.core.command

enum class CommandPhase(
    val order: Int
) {
    INIT(-100_000),

    PRE_VENDOR(-60_000),
    VENDOR(-40_000),
    POST_VENDOR(-20_000),

    INTERMEDIATE(0),

    PRE_EXECUTE(20_000),
    EXECUTE(40_000),
    POST_EXECUTE(60_000),

    FINALIZE(100_000)
}
