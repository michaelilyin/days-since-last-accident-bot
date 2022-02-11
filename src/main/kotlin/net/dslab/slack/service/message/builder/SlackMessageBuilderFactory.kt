package net.dslab.slack.service.message.builder

interface SlackMessageBuilderFactory {
    fun builder(): SlackMessageBuilder
}
