package net.dslab.core.command

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import net.dslab.common.exception.BotException
import net.dslab.core.command.exception.CommandExecutionException
import net.dslab.core.command.model.CommandExecutionInput
import net.dslab.core.command.model.KnownCommandType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import javax.inject.Inject

@QuarkusTest
internal class CommandExecutionServiceImplTest {

    @InjectMock
    internal lateinit var command: Command

    @Inject
    internal lateinit var commandExecutionService: CommandExecutionService

    @BeforeEach
    internal fun setup() {
        commandExecutionService.invalidateCache()
    }

    @Test
    internal fun runOK() {
        val input =
            CommandExecutionInput(KnownCommandType.ENABLE_TRACKING, "team-id", "chat-id")
        val builder = mock<CommandResultBuilder<*>> { }
        BDDMockito.given(command.supports(KnownCommandType.ENABLE_TRACKING))
            .willReturn(true)

        commandExecutionService.run(input, builder)

        BDDMockito.verify(command)
            .run(input, builder)
    }

    @Test
    internal fun runNotFound() {
        val input =
            CommandExecutionInput(KnownCommandType.ENABLE_TRACKING, "team-id", "chat-id")
        val builder = mock<CommandResultBuilder<*>> { }
        BDDMockito.given(command.supports(KnownCommandType.ENABLE_TRACKING))
            .willReturn(false)

        assertThrows<BotException> {
            commandExecutionService.run(input, builder)
        }

        BDDMockito.verify(command, never())
            .run(any(), any())
    }

    @Test
    internal fun runException() {
        val input =
            CommandExecutionInput(KnownCommandType.ENABLE_TRACKING, "team-id", "chat-id")
        val builder = mock<CommandResultBuilder<*>> { }
        BDDMockito.given(command.supports(KnownCommandType.ENABLE_TRACKING))
            .willReturn(true)
        BDDMockito.doThrow(RuntimeException())
            .`when`(command).run(any(), any())

        assertThrows<CommandExecutionException> {
            commandExecutionService.run(input, builder)
        }
    }
}
