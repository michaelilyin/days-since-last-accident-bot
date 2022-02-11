package net.dslab.slack.service

import net.dslab.core.command.Command
import net.dslab.core.command.CommandChain
import net.dslab.core.command.CommandPhase
import net.dslab.core.command.CommandPriority
import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.message.builder.MessageBuilder
import net.dslab.slack.model.KnownInteractionCallbackId
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@CommandPriority(CommandPhase.VENDOR)
class AddKeywordInteractionCommand : Command {
    override fun run(
        context: CommandExecutionContext,
        resultBuilder: MessageBuilder<*>,
        next: CommandChain
    ) {
        if (context.type != KnownInteractionCallbackId.ADD_KEYWORD) {
            next.run(context, resultBuilder)
            return
        }

        resultBuilder.paragraph {
            plainText("Input text will be here")
        }
    }
}
