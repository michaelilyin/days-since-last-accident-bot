package ilyin.core.command.impl

import ilyin.core.exception.BotException
import ilyin.core.command.Command
import ilyin.core.command.CommandExecutionService
import ilyin.core.command.CommandResultBuilder
import ilyin.core.command.exception.CommandExecutionException
import ilyin.core.command.model.CommandExecutionInput
import ilyin.core.command.model.CommandType
import mu.KLogger
import java.util.concurrent.ConcurrentHashMap
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Instance

@ApplicationScoped
class CommandExecutionServiceImpl(
    private val commands: Instance<Command>,
    private val logger: KLogger
) : CommandExecutionService {
    private val commandsCache = ConcurrentHashMap<CommandType, Command>()

    override fun run(input: CommandExecutionInput, builder: CommandResultBuilder<*>) {
        logger.info { "Invoking command for $input" }

        val command = commandsCache.computeIfAbsent(input.type) { type ->
            commands.find { it.supports(type) }
                ?: throw BotException("Could not find command for $type")
        }

        try {
            command.run(input, builder)
            logger.info { "Invocation of command for $input is successful" }
        } catch (e: Exception) {
            throw CommandExecutionException(input, e)
        }
    }
}
