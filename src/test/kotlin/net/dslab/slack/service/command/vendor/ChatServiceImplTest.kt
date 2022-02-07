package net.dslab.slack.service.command.vendor

import com.slack.api.RequestConfigurator
import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.conversations.ConversationsInfoRequest
import com.slack.api.methods.request.conversations.ConversationsJoinRequest
import com.slack.api.methods.response.conversations.ConversationsInfoResponse
import com.slack.api.methods.response.conversations.ConversationsJoinResponse
import com.slack.api.model.Conversation
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import net.dslab.core.command.vendor.ChatService
import net.dslab.slack.integration.exception.SlackTextApiException
import net.dslab.slack.service.auth.SlackAuthTokenRegistry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import org.mockito.BDDMockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import javax.inject.Inject

typealias ConversationRequestBuilder = ConversationsInfoRequest.ConversationsInfoRequestBuilder
typealias ConversationJoinBuilder = ConversationsJoinRequest.ConversationsJoinRequestBuilder

@QuarkusTest
internal class ChatServiceImplTest {

    @InjectMock
    internal lateinit var authTokenRegistry: SlackAuthTokenRegistry

    @InjectMock
    internal lateinit var slack: Slack

    @Inject
    internal lateinit var chatService: ChatService

    private lateinit var requestBuilderMock: ConversationRequestBuilder
    private lateinit var joinBuilderMock: ConversationJoinBuilder
    private lateinit var methodsClientMock: MethodsClient

    @BeforeEach
    internal fun setup() {
        requestBuilderMock = mock {
            on { token(any()) }.thenReturn(mock)
            on { channel(any()) }.thenReturn(mock)
        }
        joinBuilderMock = mock {
            on { token(any()) }.thenReturn(mock)
            on { channel(any()) }.thenReturn(mock)
        }
        methodsClientMock = mock()
        BDDMockito.given(slack.methods())
            .willReturn(methodsClientMock)
    }

    @Test
    internal fun getChatInfoOK() {
        val teamId = "team-id"
        val chatId = "chat-id"
        val chatName = "chat-name"
        val isMember = true
        val token = "token"
        BDDMockito.given(authTokenRegistry.getAuthToken(teamId))
            .willReturn(token)
        val channel = mock<Conversation> {
            on { it.id }.thenReturn(chatId)
            on { it.name }.thenReturn(chatName)
            on { it.isMember }.thenReturn(isMember)
        }
        val response = mock<ConversationsInfoResponse> {
            on { it.isOk }.thenReturn(true)
            on { it.channel }.thenReturn(channel)
        }
        mockConversationResponse(response)

        val res = chatService.getChatInfo(teamId, chatId)

        assertNotNull(res)
        assertEquals(chatId, res?.id)
        assertEquals(chatName, res?.name)
        assertEquals(isMember, res?.member)
        BDDMockito.verify(requestBuilderMock).token(token)
        BDDMockito.verify(requestBuilderMock).channel(chatId)
    }

    @Test
    internal fun getChatInfoNoK() {
        val teamId = "team-id"
        val chatId = "chat-id"
        val token = "token"
        BDDMockito.given(authTokenRegistry.getAuthToken(teamId))
            .willReturn(token)
        val response = mock<ConversationsInfoResponse> {
            on { it.isOk }.thenReturn(false)
            on { it.error }.thenReturn("unknown")
        }
        mockConversationResponse(response)

        assertThrows<SlackTextApiException> {
            chatService.getChatInfo(teamId, chatId)
        }
    }

    @Test
    internal fun getChatInfoNotFound() {
        val teamId = "team-id"
        val chatId = "chat-id"
        val token = "token"
        BDDMockito.given(authTokenRegistry.getAuthToken(teamId))
            .willReturn(token)
        val response = mock<ConversationsInfoResponse> {
            on { it.isOk }.thenReturn(false)
            on { it.error }.thenReturn("channel_not_found")
        }
        mockConversationResponse(response)

        val res = chatService.getChatInfo(teamId, chatId)

        assertNull(res)
    }

    @Test
    internal fun joinOK() {
        val teamId = "team-id"
        val chatId = "chat-id"
        val token = "token"

        BDDMockito.given(authTokenRegistry.getAuthToken(teamId))
            .willReturn(token)
        val response = mock<ConversationsJoinResponse> {
            on { it.isOk }.thenReturn(true)
        }
        mockConversationJoinResponse(response)

        chatService.join(teamId, chatId)

        BDDMockito.verify(methodsClientMock)
            .conversationsJoin(any<RequestConfigurator<ConversationJoinBuilder>>())
        BDDMockito.verify(joinBuilderMock).token(token)
        BDDMockito.verify(joinBuilderMock).channel(chatId)
    }

    @Test
    internal fun joinNoK() {
        val teamId = "team-id"
        val chatId = "chat-id"
        val token = "token"

        BDDMockito.given(authTokenRegistry.getAuthToken(teamId))
            .willReturn(token)
        val response = mock<ConversationsJoinResponse> {
            on { it.isOk }.thenReturn(false)
        }
        mockConversationJoinResponse(response)

        assertThrows<SlackTextApiException> {
            chatService.join(teamId, chatId)
        }
        BDDMockito.verify(joinBuilderMock).token(token)
        BDDMockito.verify(joinBuilderMock).channel(chatId)
    }

    private fun mockConversationResponse(response: ConversationsInfoResponse) {
        BDDMockito.given(methodsClientMock.conversationsInfo(any<RequestConfigurator<ConversationRequestBuilder>>()))
            .willAnswer {
                it.getArgument<RequestConfigurator<ConversationRequestBuilder>>(0)
                    .configure(requestBuilderMock)
                response
            }
    }

    private fun mockConversationJoinResponse(response: ConversationsJoinResponse) {
        BDDMockito.given(methodsClientMock.conversationsJoin(any<RequestConfigurator<ConversationJoinBuilder>>()))
            .willAnswer {
                it.getArgument<RequestConfigurator<ConversationJoinBuilder>>(0)
                    .configure(joinBuilderMock)
                response
            }
    }
}
