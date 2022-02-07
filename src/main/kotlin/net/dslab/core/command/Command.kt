package net.dslab.core.command

import net.dslab.core.command.model.CommandExecutionInput
import net.dslab.core.command.model.CommandType

interface Command {
    fun supports(type: CommandType): Boolean

    fun run(input: CommandExecutionInput, resultBuilder: CommandResultBuilder<*>)
}
