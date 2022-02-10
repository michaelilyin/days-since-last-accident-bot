package net.dslab.slack.api.http

import com.fasterxml.jackson.databind.ObjectMapper
import net.dslab.slack.model.SlackCallbackType
import mu.KLogger
import net.dslab.slack.api.http.filter.verification.VerifySlackRequests
import net.dslab.slack.api.http.model.SlackChallengeOutput
import net.dslab.slack.api.http.model.SlackCallbackInput
import net.dslab.slack.api.http.model.SlackChallengeInput
import net.dslab.slack.api.http.model.SlackEventCallbackInput
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("slack")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
class SlackEventDispatcherResource @Inject constructor(
    private val objectMapper: ObjectMapper,
    private val logger: KLogger
) {

    @POST
    @Path("event")
    @VerifySlackRequests
    fun event(event: SlackCallbackInput): Response {
        logger.debug { "Received slack event $event" }

        return when (event.type) {
            SlackCallbackType.URL_VERIFICATION -> handleChallenge(event)
            SlackCallbackType.EVENT_CALLBACK -> handleEventCallback(event)
        }
    }

    private fun handleEventCallback(valueMap: Map<*, *>): Response {
        val callback = objectMapper.convertValue(valueMap, SlackEventCallbackInput::class.java)
        logger.debug { "Received message ${callback.token} ${callback.eventTime} ${callback.event}" }

        return Response.ok().build()
    }

    private fun handleChallenge(valueMap: Map<*, *>): Response {
        val challenge = objectMapper.convertValue(valueMap, SlackChallengeInput::class.java)
        val output = SlackChallengeOutput(challenge.challenge)
        return Response.ok(output).build()
    }
}
