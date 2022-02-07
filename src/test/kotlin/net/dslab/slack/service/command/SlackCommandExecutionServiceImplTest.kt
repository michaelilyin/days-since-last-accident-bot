package net.dslab.slack.service.command

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import net.dslab.core.command.CommandExecutionService
import net.dslab.core.command.model.CommandExecutionInput
import net.dslab.core.command.model.CommandType
import net.dslab.core.command.model.KnownCommandType
import net.dslab.slack.api.http.model.SlashCommandInput
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import java.net.URI
import javax.inject.Inject

@QuarkusTest
internal class SlackCommandExecutionServiceImplTest {

    @InjectMock
    private lateinit var commandExecutionService: CommandExecutionService

    @InjectMock
    private lateinit var slackCommandResultBuilderFactory: SlackCommandResultBuilderFactory

    @Inject
    private lateinit var slackCommandExecutionService: SlackCommandExecutionService

    @Test
    internal fun runOK() {
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
        val commonCommand = CommandExecutionInput(
            type = KnownCommandType.ENABLE_TRACKING,
            teamId = "team",
            chatId = "channel-id"
        )

        val builderMock = BDDMockito.mock(SlackCommandResultBuilder::class.java)
        BDDMockito.given(slackCommandResultBuilderFactory.builder())
            .willReturn(builderMock)
        BDDMockito.given(builderMock.build())
            .willReturn("test-result")

        val res = slackCommandExecutionService.run(command)

        assertEquals("test-result", res)
        BDDMockito.verify(commandExecutionService)
            .run(commonCommand, builderMock)
    }
}
