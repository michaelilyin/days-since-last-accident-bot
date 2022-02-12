package net.dslab.slack.service.command

import com.slack.api.model.Message
import net.dslab.core.command.CommandExecutionService
import net.dslab.slack.api.http.model.SlackInteractiveCommandInput
import net.dslab.slack.api.http.model.SlashCommandInput
import net.dslab.slack.api.http.model.SlashOutput
import net.dslab.slack.service.message.builder.SlackMessageBuilderFactory
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
internal class SlackCommandExecutionServiceImpl @Inject constructor(
    private val commandExecutionService: CommandExecutionService,
    private val slackMessageBuilderFactory: SlackMessageBuilderFactory
) : SlackCommandExecutionService {

    override fun run(slackCommand: SlashCommandInput): SlashOutput {
        val builder = slackMessageBuilderFactory.builder()
        val context = RawSlackSlashCommandContext(slackCommand)

        commandExecutionService.run(context, builder)

        val blocks = builder.build()
        return SlashOutput(blocks)
    }

    override fun run(slackInteractiveCommand: SlackInteractiveCommandInput): Message {
        val builder = slackMessageBuilderFactory.builder()
        val context = RawSlackInteractiveCommandContext(slackInteractiveCommand)

        commandExecutionService.run(context, builder)

        return Message()
    }
}
