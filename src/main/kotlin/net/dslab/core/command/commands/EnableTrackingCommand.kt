package net.dslab.core.command.commands

import mu.KLogger
import net.dslab.core.command.Command
import net.dslab.core.command.CommandResultBuilder
import net.dslab.core.command.model.CommandExecutionInput
import net.dslab.core.command.model.CommandType
import net.dslab.core.command.model.KnownCommandType
import net.dslab.core.command.vendor.ChatService
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class EnableTrackingCommand @Inject constructor(
    private val chatService: ChatService,
    private val logger: KLogger
) : Command {

    override fun supports(type: CommandType): Boolean {
        return type == KnownCommandType.ENABLE_TRACKING
    }

    override fun run(input: CommandExecutionInput, resultBuilder: CommandResultBuilder<*>) {
        val chat = chatService.getChatInfo(input.teamId, input.chatId)

        if (chat == null) {
            // TODO: probably private channel, offer user to add bot at first
            logger.info { "[Chat ${input.chatId}] was not found in [Team ${input.teamId}]" }
            resultBuilder.plainText("Chat ${input.chatId} haven't been found")
            return
        }

        if (!chat.member) {
            logger.info { "Is not member of [Chat ${input.chatId}] in [Team ${input.teamId}], joining" }
            chatService.join(input.teamId, chat.id)
        }

        resultBuilder.plainText("Success");
    }
}
