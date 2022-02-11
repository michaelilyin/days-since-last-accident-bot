package net.dslab.core.command

import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.message.builder.MessageBuilder

interface CommandChain {
    fun run(context: CommandExecutionContext, resultBuilder: MessageBuilder<*>)
}

class UsualCommandChain(
    private val command: Command,
    private val next: CommandChain
) : CommandChain {
    override fun run(context: CommandExecutionContext, resultBuilder: MessageBuilder<*>) {
        command.run(context, resultBuilder, next)
    }
}

class LastCommandChain : CommandChain {
    override fun run(context: CommandExecutionContext, resultBuilder: MessageBuilder<*>) {
        // do noting
    }
}
