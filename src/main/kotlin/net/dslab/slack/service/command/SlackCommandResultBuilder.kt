package net.dslab.slack.service.command

import net.dslab.core.command.CommandResultBuilder

interface SlackCommandResultBuilder : CommandResultBuilder<SlackCommandResultBuilder> {
    fun build(): String
}
