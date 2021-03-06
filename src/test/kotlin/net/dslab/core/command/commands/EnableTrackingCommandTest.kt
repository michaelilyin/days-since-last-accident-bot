package net.dslab.core.command.commands

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import net.dslab.core.command.CommandChain
import net.dslab.core.message.builder.MessageBuilder
import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.command.model.KnownCommandType
import net.dslab.core.command.model.UnknownCommandType
import net.dslab.core.vendor.ChatService
import net.dslab.core.vendor.TrackingSettingsService
import net.dslab.core.vendor.model.ChatInfo
import net.dslab.core.vendor.model.TrackingSettings
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.willReturn
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import javax.inject.Inject

@QuarkusTest
internal class EnableTrackingCommandTest {

    @InjectMock
    internal lateinit var chatService: ChatService

    @InjectMock
    internal lateinit var trackingSettingsService: TrackingSettingsService

    @Inject
    internal lateinit var enableTrackingCommand: EnableTrackingCommand

    @Test
    internal fun runOK() {
        val teamId = "team-id"
        val chatId = "chat-id"
        val input = mock<CommandExecutionContext> {
            on { it.type }.thenReturn(KnownCommandType.ENABLE_TRACKING)
            on { it.chatId }.thenReturn(chatId)
            on { it.teamId }.thenReturn(teamId)
        }
        val builder = mock<MessageBuilder<*>> { }
        val chatInfo = mock<ChatInfo> {
            on { it.member }.thenReturn(true)
            on { it.id }.thenReturn(chatId)
        }
        BDDMockito.given(chatService.getChatInfo(teamId, chatId))
            .willReturn(chatInfo)
        val settings = mock<TrackingSettings> {
            on { it.enabled }.thenReturn(false)
        }
        BDDMockito.given(trackingSettingsService.findMainSettings(teamId, chatId))
            .willReturn(settings)

        val chain = mock<CommandChain> { }

        enableTrackingCommand.run(input, builder, chain)

        BDDMockito.verify(trackingSettingsService).enableTracking(teamId, chatId)
        BDDMockito.verify(builder).paragraph(any())
        BDDMockito.verify(chatService, never()).join(any(), any())
        BDDMockito.verifyNoMoreInteractions(builder)
        BDDMockito.verify(chain, never()).run(input, builder)
    }

    @Test
    internal fun runOKAlreadyJoined() {
        val teamId = "team-id"
        val chatId = "chat-id"
        val input = mock<CommandExecutionContext> {
            on { it.type }.thenReturn(KnownCommandType.ENABLE_TRACKING)
            on { it.chatId }.thenReturn(chatId)
            on { it.teamId }.thenReturn(teamId)
        }
        val builder = mock<MessageBuilder<*>> { }
        val chatInfo = mock<ChatInfo> {
            on { it.member }.thenReturn(true)
            on { it.id }.thenReturn(chatId)
        }
        BDDMockito.given(chatService.getChatInfo(teamId, chatId))
            .willReturn(chatInfo)
        val settings = mock<TrackingSettings> {
            on { it.enabled }.thenReturn(true)
        }
        BDDMockito.given(trackingSettingsService.findMainSettings(teamId, chatId))
            .willReturn(settings)

        val chain = mock<CommandChain> { }

        enableTrackingCommand.run(input, builder, chain)

        BDDMockito.verify(trackingSettingsService, never()).enableTracking(teamId, chatId)
        BDDMockito.verify(builder).paragraph(any())
        BDDMockito.verify(chatService, never()).join(any(), any())
        BDDMockito.verifyNoMoreInteractions(builder)
        BDDMockito.verify(chain, never()).run(input, builder)
    }

    @Test
    internal fun runNoChat() {
        val teamId = "team-id"
        val chatId = "chat-id"
        val input = mock<CommandExecutionContext> {
            on { it.type }.thenReturn(KnownCommandType.ENABLE_TRACKING)
            on { it.chatId }.thenReturn(chatId)
            on { it.teamId }.thenReturn(teamId)
        }
        val chain = mock<CommandChain> { }
        val builder = mock<MessageBuilder<*>> { }

        enableTrackingCommand.run(input, builder, chain)

        BDDMockito.verify(builder).paragraph(any())
        BDDMockito.verify(chatService, never()).join(any(), any())
        BDDMockito.verifyNoMoreInteractions(builder)
        BDDMockito.verify(chain, never()).run(input, builder)
    }

    @Test
    internal fun runIsNotAMemberOK() {
        val teamId = "team-id"
        val chatId = "chat-id"
        val input = mock<CommandExecutionContext> {
            on { it.type }.thenReturn(KnownCommandType.ENABLE_TRACKING)
            on { it.chatId }.thenReturn(chatId)
            on { it.teamId }.thenReturn(teamId)
        }
        val builder = mock<MessageBuilder<*>> { }
        val chatInfo = mock<ChatInfo> {
            on { it.member }.thenReturn(false)
            on { it.id }.thenReturn(chatId)
        }
        BDDMockito.given(chatService.getChatInfo(teamId, chatId))
            .willReturn(chatInfo)
        val settings = mock<TrackingSettings> {
            on { it.enabled }.thenReturn(false)
        }
        BDDMockito.given(trackingSettingsService.findMainSettings(teamId, chatId))
            .willReturn(settings)
        val chain = mock<CommandChain> { }

        enableTrackingCommand.run(input, builder, chain)

        BDDMockito.verify(chatService).join(teamId, chatId)
        BDDMockito.verify(builder).paragraph(any())
        BDDMockito.verifyNoMoreInteractions(builder)
        BDDMockito.verify(chain, never()).run(input, builder)
    }
}
