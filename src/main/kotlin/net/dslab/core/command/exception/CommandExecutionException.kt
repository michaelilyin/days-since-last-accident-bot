package net.dslab.core.command.exception

import net.dslab.common.exception.BotException
import net.dslab.core.command.context.CommandExecutionContext

class CommandExecutionException(
    val input: CommandExecutionContext,
    cause: Exception? = null
) : BotException("Failed to execute command for $input", cause) {
}
