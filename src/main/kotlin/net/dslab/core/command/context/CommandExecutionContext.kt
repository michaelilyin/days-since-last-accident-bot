package net.dslab.core.command.context

import net.dslab.core.command.model.CommandType
import net.dslab.core.message.builder.MessageBuilder

interface CommandExecutionContext {
    val type: CommandType
    val teamId: String
    val chatId: String
    val nested: CommandNestedContextContainer
}
