package net.dslab.slack.service.auth

import com.google.cloud.firestore.Firestore
import com.slack.api.Slack
import mu.KLogger
import net.dslab.common.exception.BotException
import net.dslab.slack.dao.SlackTeamAuthDao
import net.dslab.slack.dao.model.SlackTeamAuthEntity
import net.dslab.slack.integration.exception.SlackTextApiException
import net.dslab.slack.service.auth.model.InitialTokenRequestResult
import net.dslab.slack.service.auth.properties.SlackAuthProperties
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
internal class SlackAuthTokenRegistryImpl @Inject constructor(
    private val slack: Slack,
    private val authProperties: SlackAuthProperties,
    private val slackTeamAuthDao: SlackTeamAuthDao,
    private val logger: KLogger,
    private val firestore: Firestore
) : SlackInitialAuthenticator, SlackAuthTokenRegistry {

    override fun performInitialTokenRequest(code: String): InitialTokenRequestResult {
        return firestore.runTransaction {
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

            val auth = SlackTeamAuthEntity(tokenResponse.accessToken)
            slackTeamAuthDao.storeAuthInfo(it, tokenResponse.team.id, auth)
            logger.info { "[Team=${team.id} (${team.name})] authentication info was stored" }

            InitialTokenRequestResult(team.id, tokenResponse.botUserId)
        }.get()
    }

    override fun getAuthToken(teamId: String): String {
        return firestore.runTransaction {
            val auth = slackTeamAuthDao.getAuthInfo(it, teamId)

            auth?.accessToken ?: throw BotException("Auth info not found for $teamId")
        }.get()
    }
}
