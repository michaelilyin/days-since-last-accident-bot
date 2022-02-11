package net.dslab.core.command.commands

import mu.KLogger
import net.dslab.core.command.Command
import net.dslab.core.command.CommandChain
import net.dslab.core.command.CommandPhase
import net.dslab.core.command.CommandPriority
import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.command.model.CommandType
import net.dslab.core.command.model.KnownCommandType
import net.dslab.core.command.vendor.ChatService
import net.dslab.core.message.builder.MessageBuilder
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
@CommandPriority(CommandPhase.EXECUTE)
class EnableTrackingCommand @Inject constructor(
    private val chatService: ChatService,
    private val logger: KLogger
) : Command {

    override fun run(
        input: CommandExecutionContext,
        resultBuilder: MessageBuilder<*>,
        next: CommandChain
    ) {
        if (input.type != KnownCommandType.ENABLE_TRACKING) {
            next.run(input, resultBuilder)
            return
        }
        val chat = chatService.getChatInfo(input.teamId, input.chatId)

        if (chat == null) {
            // TODO: probably private channel, offer user to add bot at first
            logger.info { "[Chat ${input.chatId}] was not found in [Team ${input.teamId}]" }
            resultBuilder.paragraph {
                plainText("Chat ${input.chatId} haven't been found")
            }
            return
        }

        if (!chat.member) {
            logger.info { "Is not member of [Chat ${input.chatId}] in [Team ${input.teamId}], joining" }
            chatService.join(input.teamId, chat.id)
        }

        resultBuilder.paragraph {
            plainText("Success")
        }
    }

}
