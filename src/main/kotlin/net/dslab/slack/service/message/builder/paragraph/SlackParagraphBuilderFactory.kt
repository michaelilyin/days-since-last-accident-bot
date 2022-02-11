package net.dslab.slack.service.message.builder.paragraph

interface SlackParagraphBuilderFactory {
    fun builder(): SlackParagraphBuilder
}
