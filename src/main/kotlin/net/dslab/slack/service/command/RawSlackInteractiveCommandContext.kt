package net.dslab.slack.service.command

import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.command.context.CommandNestedContextContainer
import net.dslab.core.command.model.CommandType
import net.dslab.slack.api.http.model.SlackInteractiveCommandInput

class RawSlackInteractiveCommandContext(
    override val chatId: String,
    override val teamId: String,
    override val type: CommandType
) : CommandExecutionContext {
    override val nested = CommandNestedContextContainer()

    constructor(input: SlackInteractiveCommandInput) : this(
        teamId = input.team.id,
        chatId = TODO("must be implemented"),
        type = input.callbackId
    )
}
