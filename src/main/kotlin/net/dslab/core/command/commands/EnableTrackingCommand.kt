package net.dslab.core.command.commands

import mu.KLogger
import net.dslab.core.command.Command
import net.dslab.core.command.CommandChain
import net.dslab.core.command.CommandPhase
import net.dslab.core.command.CommandPriority
import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.command.model.CommandType
import net.dslab.core.command.model.KnownCommandType
import net.dslab.core.vendor.ChatService
import net.dslab.core.message.builder.MessageBuilder
import net.dslab.core.vendor.TrackingSettingsService
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
@CommandPriority(CommandPhase.EXECUTE)
class EnableTrackingCommand @Inject constructor(
    private val chatService: ChatService,
    private val logger: KLogger,
    private val trackingSettingsService: TrackingSettingsService
) : Command {

    override fun run(
        context: CommandExecutionContext,
        resultBuilder: MessageBuilder<*>,
        next: CommandChain
    ) {
        if (context.type != KnownCommandType.ENABLE_TRACKING) {
            next.run(context, resultBuilder)
            return
        }
        val chat = chatService.getChatInfo(context.teamId, context.chatId)

        if (chat == null) {
            // TODO: probably private channel, offer user to add bot at first
            logger.info { "[Chat ${context.chatId}] was not found in [Team ${context.teamId}]" }
            resultBuilder.paragraph {
                plainText("Chat ${context.chatId} haven't been found")
            }
            return
        }

        if (!chat.member) {
            logger.info { "Is not member of [Chat ${context.chatId}] in [Team ${context.teamId}], joining" }
            chatService.join(context.teamId, chat.id)
        }

        val settings = trackingSettingsService.findMainSettings(context.teamId, chat.id)

        if (!settings.enabled) {
            trackingSettingsService.enableTracking(context.teamId, chat.id)

            resultBuilder.paragraph {
                plainText("Tracking has been enabled!")
            }
        } else {
            resultBuilder.paragraph {
                plainText("Tracking is already enabled!")
            }
        }
    }

}
