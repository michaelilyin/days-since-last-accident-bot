package ilyin.slack.inbound.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ilyin.core.command.model.CommandExecutionInput
import ilyin.core.command.model.CommandType
import ilyin.core.command.model.KnownCommandType
import ilyin.core.command.model.UnknownCommandType
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
    fun toServiceInput(): CommandExecutionInput {
        return CommandExecutionInput(
            type = convertType(command),
            teamId = teamId,
            chatId = channelId
        )
    }

    private fun convertType(command: String): CommandType {
        return when (command) {
            "/enable-days-since-counter" -> KnownCommandType.ENABLE_TRACKING
            else -> UnknownCommandType(command)
        }
    }
}
