package net.dslab.slack.api.http

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogger
import net.dslab.slack.api.http.filter.verification.VerifySlackRequests
import net.dslab.slack.api.http.model.SlackInteractiveCommandInput
import net.dslab.slack.api.http.model.SlashCommandInput
import net.dslab.slack.service.command.SlackCommandExecutionService
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Form
import javax.ws.rs.core.MediaType

@Path("slack")
@ApplicationScoped
class SlackCommandResource @Inject constructor(
    private val logger: KLogger,
    private val objectMapper: ObjectMapper,
    private val slackCommandExecutionService: SlackCommandExecutionService
) {
    @POST
    @Path("slash")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @VerifySlackRequests
    fun slash(form: Form): String {
        val map = form.asMap()
        logger.debug { "Received form $map" }
        val slackCommand = objectMapper.convertValue(map, SlashCommandInput::class.java)
        logger.debug { "Received command $slackCommand" }

        return slackCommandExecutionService.run(slackCommand)
    }

    @POST
    @Path("interactive")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @VerifySlackRequests
    fun interactive(form: Form) {
        val map = form.asMap()
        logger.debug { "Received form $map" }

        val interactiveCommand = objectMapper.convertValue(map, SlackInteractiveCommandInput::class.java)
        logger.debug { "Received command $interactiveCommand" }
    }
}
