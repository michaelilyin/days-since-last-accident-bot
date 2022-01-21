package ilyin.slack.inbound

import com.fasterxml.jackson.databind.ObjectMapper
import ilyin.core.command.CommandExecutionService
import ilyin.slack.service.command.SlackCommandResultBuilderFactory
import ilyin.slack.inbound.dto.SlashCommandInput
import ilyin.slack.inbound.filter.VerifySlackRequests
import mu.KLogger
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Form
import javax.ws.rs.core.MediaType

@Path("slack")
@ApplicationScoped
class SlackSlashCommandResource(
    private val logger: KLogger,
    private val objectMapper: ObjectMapper,
    private val commandExecutionService: CommandExecutionService,
    private val slackCommandResultBuilderFactory: SlackCommandResultBuilderFactory
) {
    @POST
    @Path("slash")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @VerifySlackRequests
    fun slash(form: Form): String {
        val map = form.asMap()
        logger.info { "Received form $map" }
        val slackCommand = objectMapper.convertValue(map, SlashCommandInput::class.java)
        logger.info { "Received command $slackCommand" }

        val command = slackCommand.toServiceInput()
        val builder = slackCommandResultBuilderFactory.newBuilder()

        commandExecutionService.run(command, builder)

        return builder.build()
    }
}
