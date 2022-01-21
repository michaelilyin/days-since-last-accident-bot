package ilyin.slack.service.command

import ilyin.core.command.CommandResultBuilder

interface SlackCommandResultBuilder : CommandResultBuilder<SlackCommandResultBuilder> {
    fun build(): String
}
