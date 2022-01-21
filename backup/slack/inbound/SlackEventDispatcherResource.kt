package ilyin.slack.inbound

import com.fasterxml.jackson.databind.ObjectMapper
import ilyin.slack.inbound.dto.ChallengeOutput
import ilyin.slack.inbound.dto.SlackCallbackInput
import ilyin.slack.inbound.dto.SlackChallengeInput
import ilyin.slack.inbound.dto.SlackEventCallbackInput
import ilyin.slack.inbound.filter.VerifySlackRequests
import ilyin.slack.model.SlackCallbackType
import mu.KLogger
import javax.enterprise.context.ApplicationScoped
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
class SlackEventDispatcherResource(
    private val objectMapper: ObjectMapper,
    private val logger: KLogger
) {

    @POST
    @Path("event")
    @VerifySlackRequests
    fun event(event: SlackCallbackInput): Response {
        logger.info { "Received slack event $event" }

        return when (event.type) {
            SlackCallbackType.URL_VERIFICATION -> handleChallenge(event)
            SlackCallbackType.EVENT_CALLBACK -> handleEventCallback(event)
        }
    }

    private fun handleEventCallback(valueMap: Map<*, *>): Response {
        val callback = objectMapper.convertValue(valueMap, SlackEventCallbackInput::class.java)
        logger.info { "Received message ${callback.token} ${callback.eventTime} ${callback.event}" }
        return Response.ok().build()
    }

    private fun handleChallenge(valueMap: Map<*, *>): Response {
        val challenge = objectMapper.convertValue(valueMap, SlackChallengeInput::class.java)
        val output = ChallengeOutput(challenge.challenge)
        return Response.ok(output).build()
    }
}
