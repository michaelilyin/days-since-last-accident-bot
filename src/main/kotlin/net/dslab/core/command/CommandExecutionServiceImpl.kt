package net.dslab.core.command

import mu.KLogger
import net.dslab.core.command.context.CommandExecutionContext
import net.dslab.core.command.exception.CommandExecutionException
import net.dslab.core.message.builder.MessageBuilder
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Instance
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.superclasses

@ApplicationScoped
class CommandExecutionServiceImpl @Inject constructor(
    commands: Instance<Command>,
    private val logger: KLogger
) : CommandExecutionService {
    private val chain: CommandChain = commands.asSequence()
        .sortedBy {
            val priority = getPriority(it)
            -1 * (priority.phase.order + priority.factor)
        }
        .fold(LastCommandChain() as CommandChain) { acc, command ->
            UsualCommandChain(command, acc)
        }

    private fun getPriority(command: Command): CommandPriority {
        var klass = command::class
        val priority = findForClass(klass)



        if (priority == null) {
            throw IllegalArgumentException("${klass.simpleName} must be annotated with @CommandPriority")
        }
        return priority
    }

    private fun findForClass(klass: KClass<*>): CommandPriority? {
        val priority = klass.findAnnotation<CommandPriority>()

        if (priority != null) {
            return priority
        }

        val superclasses = klass.superclasses
        for (superclass in superclasses) {
            val superPriority = findForClass(superclass)

            if (superPriority != null) {
                return superPriority
            }
        }

        return null
    }

    override fun run(input: CommandExecutionContext, builder: MessageBuilder<*>) {
        logger.info { "Invoking command for $input" }

        try {
            chain.run(input, builder)
            logger.info { "Invocation of command for $input is successful" }
        } catch (e: Exception) {
            throw CommandExecutionException(input, e)
        }
    }
}
