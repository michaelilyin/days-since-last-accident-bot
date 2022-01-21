package ilyin.slack.service.core.impl

import com.slack.api.Slack
import ilyin.core.outbound.ChatService
import ilyin.core.outbound.model.ChatInfo
import ilyin.slack.exception.SlackTextApiException
import ilyin.slack.service.auth.SlackAuthService
import ilyin.slack.service.core.impl.model.SlackChatInfo
import mu.KLogger
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SlackChatServiceImpl(
    private val slackAuthService: SlackAuthService,
    private val slack: Slack,
    private val logger: KLogger
) : ChatService {

    override fun getChatInfo(teamId: String, chatId: String): ChatInfo? {
        logger.info { "Getting info for channel $chatId" }

        val conversationsInfo = slack.methods().conversationsInfo {
            it.token(slackAuthService.getAuthToken(teamId))
                .channel(chatId)
        }

        if (conversationsInfo.isOk) {
            logger.info { "Successful result is received: ${conversationsInfo.channel}" }
            return SlackChatInfo(conversationsInfo.channel)
        }

        if (conversationsInfo.error == "channel_not_found") {
            return null
        }

        throw SlackTextApiException("Error during getting chat info for $chatId", conversationsInfo)
    }

    override fun join(teamId: String, chatId: String) {
        val result = slack.methods().conversationsJoin {
            it.token(slackAuthService.getAuthToken(teamId))
                .channel(chatId)
        }

        if (!result.isOk) {
            throw SlackTextApiException("Error during joining chat $chatId", result)
        }
    }
}
