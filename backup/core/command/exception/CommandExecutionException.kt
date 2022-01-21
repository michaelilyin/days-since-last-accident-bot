package ilyin.core.command.exception

import ilyin.core.exception.BotException
import ilyin.core.command.model.CommandExecutionInput

class CommandExecutionException(
    val input: CommandExecutionInput,
    cause: Exception? = null
) : BotException("Failed to execute command for $input", cause) {
}
