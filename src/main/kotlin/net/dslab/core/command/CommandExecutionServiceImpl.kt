package net.dslab.core.command

import mu.KLogger
import net.dslab.common.exception.BotException
import net.dslab.core.command.exception.CommandExecutionException
import net.dslab.core.command.model.CommandExecutionInput
import net.dslab.core.command.model.CommandType
import java.util.concurrent.ConcurrentHashMap
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Instance
import javax.inject.Inject

@ApplicationScoped
class CommandExecutionServiceImpl @Inject constructor(
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

    override fun invalidateCache() {
        commandsCache.clear()
    }
}
