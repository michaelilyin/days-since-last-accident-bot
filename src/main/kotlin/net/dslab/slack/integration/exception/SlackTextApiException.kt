package net.dslab.slack.integration.exception

import com.slack.api.methods.SlackApiTextResponse
import net.dslab.common.exception.BotException

open class SlackTextApiException(
    message: String,
    val response: SlackApiTextResponse
) : BotException("$message\nErroneous response from Slack: ${response.error}") {
}
