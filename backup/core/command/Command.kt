package ilyin.core.command

import ilyin.core.command.model.CommandExecutionInput
import ilyin.core.command.model.CommandType

interface Command {
    fun supports(type: CommandType): Boolean

    fun run(input: CommandExecutionInput, resultBuilder: CommandResultBuilder<*>)
}
