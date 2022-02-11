package net.dslab.slack.service.message.builder

import com.slack.api.model.Message
import net.dslab.core.message.builder.MessageBuilder

interface SlackMessageBuilder : MessageBuilder<SlackMessageBuilder> {
    fun build(): Message
}
