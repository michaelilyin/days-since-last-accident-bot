package net.dslab.slack.api.http.model

import com.fasterxml.jackson.annotation.JsonProperty
import net.dslab.slack.model.InteractionCallbackId
import net.dslab.slack.model.InteractionType

data class SlackInteractiveCommandInput(
    val type: InteractionType,
    @JsonProperty("callback_id")
    val callbackId: InteractionCallbackId,
    val token: String,
    @JsonProperty("action_ts")
    val actionTs: String,
    @JsonProperty("trigger_id")
    val triggerId: String,
    val team: TeamSimple,
    val user: UserSimple
)
