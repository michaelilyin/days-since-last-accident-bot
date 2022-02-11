package net.dslab.core.command

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import net.dslab.common.exception.BotException
import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.command.exception.CommandExecutionException
import net.dslab.core.command.model.KnownCommandType
import net.dslab.core.message.builder.MessageBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import javax.inject.Inject

@QuarkusTest
internal class CommandExecutionServiceImplTest {

    @InjectMock
    internal lateinit var command: Command

    @Inject
    internal lateinit var commandExecutionService: CommandExecutionService

    @Test
    internal fun runOK() {
        val input = mock<CommandExecutionContext> {
            on { type }.thenReturn(KnownCommandType.ENABLE_TRACKING)
        }
        val builder = mock<MessageBuilder<*>> { }

        commandExecutionService.run(input, builder)

        BDDMockito.verify(command)
            .run(eq(input), eq(builder), any())
    }

    @Test
    internal fun runException() {
        val input = mock<CommandExecutionContext> {
            on { type }.thenReturn(KnownCommandType.ENABLE_TRACKING)
        }
        val builder = mock<MessageBuilder<*>> { }
        BDDMockito.doThrow(RuntimeException())
            .`when`(command).run(any(), any(), any())

        assertThrows<CommandExecutionException> {
            commandExecutionService.run(input, builder)
        }
    }
}
