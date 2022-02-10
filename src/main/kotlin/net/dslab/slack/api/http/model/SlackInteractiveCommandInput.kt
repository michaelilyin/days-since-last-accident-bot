package net.dslab.slack.api.http.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SlackInteractiveCommandInput(
    val type: String,
    @JsonProperty("callback_id")
    val callbackId: String
)
