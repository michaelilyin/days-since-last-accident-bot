package net.dslab.slack.service.command.vendor

import com.slack.api.Slack
import mu.KLogger
import net.dslab.core.command.vendor.ChatService
import net.dslab.core.command.vendor.model.ChatInfo
import net.dslab.slack.integration.exception.SlackTextApiException
import net.dslab.slack.service.auth.SlackAuthTokenRegistry
import net.dslab.slack.service.command.vendor.model.SlackChatInfo
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ChatServiceImpl @Inject constructor(
    private val authTokenRegistry: SlackAuthTokenRegistry,
    private val slack: Slack,
    private val logger: KLogger
) : ChatService {

    override fun getChatInfo(teamId: String, chatId: String): ChatInfo? {
        logger.info { "Getting info for channel $chatId" }

        val conversationsInfo = slack.methods().conversationsInfo {
            it.token(authTokenRegistry.getAuthToken(teamId))
                .channel(chatId)
        }

        if (conversationsInfo.isOk) {
            logger.info { "Successful result is received: ${conversationsInfo.channel}" }
            return SlackChatInfo(conversationsInfo.channel)
        }

        if (conversationsInfo.error == "channel_not_found") {
            return null
        }

        if (conversationsInfo.error == "invalid_auth") {
            TODO("read and fix")
        }

        throw SlackTextApiException("Error during getting chat info for $chatId", conversationsInfo)
    }

    override fun join(teamId: String, chatId: String) {
        val result = slack.methods().conversationsJoin {
            it.token(authTokenRegistry.getAuthToken(teamId))
                .channel(chatId)
        }

        if (!result.isOk) {
            throw SlackTextApiException("Error during joining chat $chatId", result)
        }
    }
}
