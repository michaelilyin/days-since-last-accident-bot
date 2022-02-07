package net.dslab.core.command.exception

import net.dslab.common.exception.BotException
import net.dslab.core.command.model.CommandExecutionInput

class CommandExecutionException(
    val input: CommandExecutionInput,
    cause: Exception? = null
) : BotException("Failed to execute command for $input", cause) {
}
