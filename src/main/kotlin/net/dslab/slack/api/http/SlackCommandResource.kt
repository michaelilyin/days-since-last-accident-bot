package net.dslab.slack.api.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.readValue
import com.slack.api.model.Message
import mu.KLogger
import net.dslab.slack.api.http.filter.verification.VerifySlackRequests
import net.dslab.slack.api.http.model.SlackInteractiveCommandInput
import net.dslab.slack.api.http.model.SlashCommandInput
import net.dslab.slack.api.http.model.SlashOutput
import net.dslab.slack.service.command.SlackCommandExecutionService
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Form
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

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
    @Produces(MediaType.APPLICATION_JSON)
    @VerifySlackRequests
    fun slash(form: Form): SlashOutput {
        val map = form.asMap()
        logger.debug { "Received form $map" }
        val slackCommand = objectMapper.convertValue<SlashCommandInput>(map)
        logger.debug { "Received command $slackCommand" }

        return slackCommandExecutionService.run(slackCommand)
    }

    @POST
    @Path("interactive")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @VerifySlackRequests
    fun interactive(form: Form): Response {
        val map = form.asMap()
        logger.debug { "Received form $map" }

        val payload = map["payload"]
        if (payload == null || payload.size != 1) {
            return Response.status(400).build()
        }

        val singlePayloadValue = payload.first()
        val interactiveCommand =
            objectMapper.readValue<SlackInteractiveCommandInput>(singlePayloadValue)
        logger.debug { "Received command $interactiveCommand" }

        val result = slackCommandExecutionService.run(interactiveCommand)
        return Response.ok(result).build()
    }
}
