package net.dslab.slack.api.http.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URI

data class SlashCommandInput(
    val token: String,
    @JsonProperty("team_id")
    val teamId: String,
    @JsonProperty("team_domain")
    val teamDomain: String,
    @JsonProperty("enterprise_id")
    val enterpriseId: String?,
    @JsonProperty("enterprise_name")
    val enterpriseName: String?,
    @JsonProperty("channel_id")
    val channelId: String,
    @JsonProperty("channel_name")
    val channelName: String,
    @JsonProperty("user_id")
    val userId: String,
    @JsonProperty("user_name")
    val userName: String,
    val command: String,
    val text: String?,
    @JsonProperty("response_url")
    val responseUrl: URI,
    @JsonProperty("trigger_id")
    val triggerId: String,
    @JsonProperty("app_id")
    val appId: String?
) {
}
