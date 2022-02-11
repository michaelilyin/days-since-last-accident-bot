package net.dslab.core.command

annotation class CommandPriority(
    val phase: CommandPhase,
    val factor: Int = 0
) {
    companion object Factor {
        const val BEFORE = -1
        const val AFTER = 1
    }
}
