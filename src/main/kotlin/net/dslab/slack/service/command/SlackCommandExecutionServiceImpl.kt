package net.dslab.slack.service.command

import net.dslab.core.command.CommandExecutionService
import net.dslab.slack.api.http.model.SlashCommandInput
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
internal class SlackCommandExecutionServiceImpl @Inject constructor(
    private val commandExecutionService: CommandExecutionService,
    private val slackCommandResultBuilderFactory: SlackCommandResultBuilderFactory
) : SlackCommandExecutionService {

    override fun run(slackCommand: SlashCommandInput): String {
        val command = slackCommand.toServiceInput()
        val builder = slackCommandResultBuilderFactory.builder()

        commandExecutionService.run(command, builder)

        return builder.build()
    }
}
