package net.dslab.core.command

import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.message.builder.MessageBuilder

interface CommandExecutionService {
    fun run(input: CommandExecutionContext, builder: MessageBuilder<*>)
}
