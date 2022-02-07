package net.dslab.core.command.commands

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import net.dslab.core.command.CommandResultBuilder
import net.dslab.core.command.model.CommandExecutionInput
import net.dslab.core.command.model.KnownCommandType
import net.dslab.core.command.model.UnknownCommandType
import net.dslab.core.command.vendor.ChatService
import net.dslab.core.command.vendor.model.ChatInfo
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import javax.inject.Inject

@QuarkusTest
internal class EnableTrackingCommandTest {

    @InjectMock
    internal lateinit var chatService: ChatService

    @Inject
    internal lateinit var enableTrackingCommand: EnableTrackingCommand

    @Test
    internal fun supportsOK() {
        val res = enableTrackingCommand.supports(KnownCommandType.ENABLE_TRACKING)

        assertTrue(res)
    }

    @Test
    internal fun supportsNoK() {
        val res = enableTrackingCommand.supports(UnknownCommandType("any"))

        assertFalse(res)
    }

    @Test
    internal fun runOK() {
        val input = CommandExecutionInput(KnownCommandType.ENABLE_TRACKING, "team-id", "chat-id")
        val builder = mock<CommandResultBuilder<*>> { }
        val chatInfo = mock<ChatInfo> {
            on { member }.thenReturn(true)
            on { id }.thenReturn(input.chatId)
        }
        BDDMockito.given(chatService.getChatInfo(input.teamId, input.chatId))
            .willReturn(chatInfo)

        enableTrackingCommand.run(input, builder)

        BDDMockito.verify(builder).plainText("Success")
        BDDMockito.verify(chatService, never()).join(any(), any())
        BDDMockito.verifyNoMoreInteractions(builder)
    }

    @Test
    internal fun runNoChat() {
        val input = CommandExecutionInput(KnownCommandType.ENABLE_TRACKING, "team-id", "chat-id")
        val builder = mock<CommandResultBuilder<*>> { }

        enableTrackingCommand.run(input, builder)

        BDDMockito.verify(builder).plainText("Chat ${input.chatId} haven't been found")
        BDDMockito.verify(chatService, never()).join(any(), any())
        BDDMockito.verifyNoMoreInteractions(builder)
    }

    @Test
    internal fun runIsNotAMemberOK() {
        val input = CommandExecutionInput(KnownCommandType.ENABLE_TRACKING, "team-id", "chat-id")
        val builder = mock<CommandResultBuilder<*>> { }
        val chatInfo = mock<ChatInfo> {
            on { member }.thenReturn(false)
            on { id }.thenReturn(input.chatId)
        }
        BDDMockito.given(chatService.getChatInfo(input.teamId, input.chatId))
            .willReturn(chatInfo)

        enableTrackingCommand.run(input, builder)

        BDDMockito.verify(chatService).join(input.teamId, input.chatId)
        BDDMockito.verify(builder).plainText("Success")
        BDDMockito.verifyNoMoreInteractions(builder)
    }
}
