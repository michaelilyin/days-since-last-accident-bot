package ilyin.slack.service.auth

import com.slack.api.Slack
import ilyin.core.exception.BotException
import ilyin.slack.configuration.properties.SlackAuthProperties
import ilyin.slack.exception.SlackTextApiException
import ilyin.slack.service.auth.dao.SlackAuthDao
import ilyin.slack.service.auth.model.InitialTokenRequestResult
import ilyin.slack.service.auth.model.SlackAuthData
import mu.KLogger
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SlackAuthServiceImpl(
    private val slack: Slack,
    private val authProperties: SlackAuthProperties,
    private val slackAuthDao: SlackAuthDao,
    private val logger: KLogger
) : SlackInitialAuthService, SlackAuthService {
    override fun performInitialTokenRequest(code: String): InitialTokenRequestResult {
        logger.info { "Received authorisation callback, requesting access token" }
        val tokenResponse = slack.methods().oauthV2Access {
            it.code(code)
                .clientId(authProperties.clientId())
                .clientSecret(authProperties.clientSecret())
                .redirectUri(authProperties.redirectUri())
        }

        if (!tokenResponse.isOk) {
            throw SlackTextApiException("Token request has failed", tokenResponse)
        }

        val team = tokenResponse.team
        val user = tokenResponse.authedUser
        logger.info { "[Team=${team.id} (${team.name})] was successfully authenticated by [User=${user.id}]" }

        val auth = SlackAuthData(tokenResponse.accessToken)
        slackAuthDao.storeAuthInfo(tokenResponse.team.id, auth)
        logger.info { "[Team=${team.id} (${team.name})] authentication info was stored" }

        return InitialTokenRequestResult(team.id, tokenResponse.botUserId)
    }

    override fun getAuthToken(teamId: String): String {
        val auth = slackAuthDao.getAuthInfo(teamId)

        return auth?.accessToken ?: throw BotException("Auth info not found for $teamId")
    }
}
