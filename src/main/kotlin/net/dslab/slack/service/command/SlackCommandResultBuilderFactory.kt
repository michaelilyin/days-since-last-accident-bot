package net.dslab.slack.service.command

interface SlackCommandResultBuilderFactory {
    fun builder(): SlackCommandResultBuilder
}
