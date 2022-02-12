package net.dslab.slack.service.command

import com.slack.api.model.Message
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import net.dslab.core.command.CommandExecutionService
import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.command.model.KnownCommandType
import net.dslab.slack.api.http.model.SlashCommandInput
import net.dslab.slack.api.http.model.SlashOutput
import net.dslab.slack.service.message.builder.SlackMessageBuilder
import net.dslab.slack.service.message.builder.SlackMessageBuilderFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import java.net.URI
import javax.inject.Inject

@QuarkusTest
internal class SlackCommandExecutionServiceImplTest {

    @InjectMock
    private lateinit var commandExecutionService: CommandExecutionService

    @InjectMock
    private lateinit var slackMessageBuilderFactory: SlackMessageBuilderFactory

    @Inject
    private lateinit var slackCommandExecutionService: SlackCommandExecutionService

    @Test
    internal fun runOK() {
        val expectedResult = SlashOutput(listOf())
        val command = SlashCommandInput(
            token = "token",
            teamId = "team",
            teamDomain = "domain",
            enterpriseId = null,
            enterpriseName = null,
            channelId = "channel-id",
            channelName = "channel-name",
            userId = "user-id",
            userName = "user-name",
            command = "/enable-days-since-counter",
            text = null,
            responseUrl = URI("http://localhost"),
            triggerId = "trigger",
            appId = null
        )

        val builderMock = BDDMockito.mock(SlackMessageBuilder::class.java)
        BDDMockito.given(slackMessageBuilderFactory.builder())
            .willReturn(builderMock)
        BDDMockito.given(builderMock.build())
            .willReturn(expectedResult.blocks)

        val res = slackCommandExecutionService.run(command)

        assertEquals(expectedResult, res)
        BDDMockito.verify(commandExecutionService)
            .run(argThat {
                type == KnownCommandType.ENABLE_TRACKING
                        && chatId == command.channelId
                        && teamId == command.teamId
            }, eq(builderMock))
    }
}
