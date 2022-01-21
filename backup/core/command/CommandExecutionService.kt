package ilyin.core.command

import ilyin.core.command.model.CommandExecutionInput

interface CommandExecutionService {
    fun run(input: CommandExecutionInput, builder: CommandResultBuilder<*>)
}
