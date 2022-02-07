package net.dslab.core.command

import net.dslab.core.command.model.CommandExecutionInput

interface CommandExecutionService {
    fun run(input: CommandExecutionInput, builder: CommandResultBuilder<*>)
    fun invalidateCache()
}
