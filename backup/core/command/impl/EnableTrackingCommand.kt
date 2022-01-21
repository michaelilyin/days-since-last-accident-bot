package ilyin.core.command.impl

import com.sun.xml.bind.v2.WellKnownNamespace
import ilyin.core.command.Command
import ilyin.core.command.CommandResultBuilder
import ilyin.core.command.model.CommandExecutionInput
import ilyin.core.command.model.CommandType
import ilyin.core.command.model.KnownCommandType
import ilyin.core.outbound.ChatService
import mu.KLogger
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class EnableTrackingCommand(
    private val chatService: ChatService,
    private val logger: KLogger
) : Command {
    override fun supports(type: CommandType): Boolean {
        return type == KnownCommandType.ENABLE_TRACKING
    }

    override fun run(input: CommandExecutionInput, resultBuilder: CommandResultBuilder<*>) {
        val chat = chatService.getChatInfo(input.teamId, input.chatId)

        if (chat == null) {
            logger.info { "[Chat ${input.chatId}] was not found in [Team ${input.teamId}]" }
            // TODO: response
            return
        }

        if (!chat.member) {
            logger.info { "Is not member of [Chat ${input.chatId}] in [Team ${input.teamId}], joining" }
            chatService.join(input.teamId, chat.id)
        }

        // TODO: response
    }
}
