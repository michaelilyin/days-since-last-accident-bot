package net.dslab.slack.service.command

import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.command.context.CommandNestedContextContainer
import net.dslab.core.command.model.CommandType
import net.dslab.core.command.model.KnownCommandType
import net.dslab.core.command.model.UnknownCommandType
import net.dslab.core.message.builder.MessageBuilder
import net.dslab.slack.api.http.model.SlashCommandInput

class RawSlackSlashCommandContext(
    override val chatId: String,
    override val teamId: String,
    override val type: CommandType
) : CommandExecutionContext {
    override val nested = CommandNestedContextContainer()

    companion object {
        private fun convertType(command: String): CommandType {
            return when (command) {
                "/enable-days-since-counter" -> KnownCommandType.ENABLE_TRACKING
                else -> UnknownCommandType(command)
            }
        }
    }

    constructor(commandInput: SlashCommandInput) : this(
        chatId = commandInput.channelId,
        teamId = commandInput.teamId,
        type = convertType(commandInput.command)
    )
}
