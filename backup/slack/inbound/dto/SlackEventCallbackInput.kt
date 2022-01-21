package ilyin.slack.inbound.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class SlackEventCallbackInput(
    val token: String,
    val event: Map<String, Any>,
    @JsonProperty("event_id")
    val eventId: String,
    @JsonProperty("event_time")
    val eventTime: Instant,
    @JsonProperty("event_context")
    val eventContext: String
) {
}
