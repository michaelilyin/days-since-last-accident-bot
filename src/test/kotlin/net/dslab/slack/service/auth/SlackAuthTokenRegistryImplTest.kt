package net.dslab.slack.service.auth

import com.slack.api.RequestConfigurator
import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.oauth.OAuthV2AccessRequest
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import net.dslab.common.exception.BotException
import net.dslab.slack.dao.SlackTeamAuthDao
import net.dslab.slack.dao.model.SlackTeamAuth
import net.dslab.slack.integration.exception.SlackTextApiException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import javax.inject.Inject

typealias OAuthBuilder = RequestConfigurator<OAuthV2AccessRequest.OAuthV2AccessRequestBuilder>

@QuarkusTest
internal class SlackAuthTokenRegistryImplTest {

    @InjectMock
    internal lateinit var slack: Slack

    @InjectMock
    internal lateinit var slackTeamAuthDao: SlackTeamAuthDao

    @Inject
    internal lateinit var slackAuthTokenRegistry: SlackAuthTokenRegistry

    @Inject
    internal lateinit var initialAuthenticator: SlackInitialAuthenticator

    private lateinit var requestBuilderMock: OAuthV2AccessRequest.OAuthV2AccessRequestBuilder
    private lateinit var methodsClientMock: MethodsClient

    @BeforeEach
    internal fun setup() {
        requestBuilderMock = mock {
            on { code(any()) }.thenReturn(this.mock)
            on { clientId(any()) }.thenReturn(this.mock)
            on { clientSecret(any()) }.thenReturn(this.mock)
            on { redirectUri(any()) }.thenReturn(this.mock)
        }
        methodsClientMock = mock()
        BDDMockito.given(slack.methods())
            .willReturn(methodsClientMock)
    }

    @Test
    fun performInitialTokenRequestOK() {
        val code = "test-code"
        val teamId = "team-id"
        val userId = "user-id"
        val botUserId = "bot-user-id"
        val accessToken = "access-token"

        val team = mock<OAuthV2AccessResponse.Team> {
            on { it.id }.thenReturn(teamId)
        }
        val user = mock<OAuthV2AccessResponse.AuthedUser> {
            on { it.id }.thenReturn(userId)
        }
        val response = mock<OAuthV2AccessResponse> {
            on { it.isOk }.thenReturn(true)
            on { it.team }.thenReturn(team)
            on { it.authedUser }.thenReturn(user)
            on { it.botUserId }.thenReturn(botUserId)
            on { it.accessToken }.thenReturn(accessToken)
        }
        mockOAuthResponse(response)

        val res = initialAuthenticator.performInitialTokenRequest(code)

        BDDMockito.verify(requestBuilderMock).code(code)
        BDDMockito.verify(requestBuilderMock).clientId("test-client")
        BDDMockito.verify(requestBuilderMock).clientSecret("test-secret")
        BDDMockito.verify(requestBuilderMock).redirectUri("http://localhost")
        BDDMockito.verify(slackTeamAuthDao).storeAuthInfo(teamId, SlackTeamAuth(accessToken))
        assertEquals(teamId, res.teamId)
        assertEquals(botUserId, res.botUserId)
    }

    @Test
    fun performInitialTokenRequestNoK() {
        val code = "test-code"

        val response = mock<OAuthV2AccessResponse> {
            on { it.isOk }.thenReturn(false)
        }
        mockOAuthResponse(response)

        assertThrows<SlackTextApiException> {
            initialAuthenticator.performInitialTokenRequest(code)
        }

        BDDMockito.verify(requestBuilderMock).code(code)
        BDDMockito.verify(requestBuilderMock).clientId("test-client")
        BDDMockito.verify(requestBuilderMock).clientSecret("test-secret")
        BDDMockito.verify(requestBuilderMock).redirectUri("http://localhost")
        BDDMockito.verify(slackTeamAuthDao, never()).storeAuthInfo(any(), any())
    }

    @Test
    fun getAuthTokenOK() {
        val teamId = "team-id"

        val auth = SlackTeamAuth("access-token")
        BDDMockito.given(slackTeamAuthDao.getAuthInfo(teamId))
            .willReturn(auth)
        val res = slackAuthTokenRegistry.getAuthToken(teamId)

        assertNotNull(res)
        assertEquals(auth.accessToken, res)
    }

    @Test
    fun getAuthTokenNoK() {
        val teamId = "team-id"

        assertThrows<BotException> {
            slackAuthTokenRegistry.getAuthToken(teamId)
        }}

    private fun mockOAuthResponse(response: OAuthV2AccessResponse) {
        BDDMockito.given(methodsClientMock.oauthV2Access(any<OAuthBuilder>()))
            .willAnswer {
                it.getArgument<OAuthBuilder>(0).configure(requestBuilderMock)
                response
            }
    }
}
