package net.dslab.core.command

import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.message.builder.MessageBuilder

interface Command {
    fun run(context: CommandExecutionContext, resultBuilder: MessageBuilder<*>, next: CommandChain)
}
