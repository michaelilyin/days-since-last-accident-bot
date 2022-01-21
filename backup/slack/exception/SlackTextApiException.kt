package ilyin.slack.exception

import com.slack.api.methods.SlackApiTextResponse
import ilyin.core.exception.BotException

open class SlackTextApiException(
    message: String,
    val response: SlackApiTextResponse
) : BotException("$message\nErroneous response from Slack: ${response.error}") {
}
