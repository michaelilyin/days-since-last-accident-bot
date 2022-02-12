package net.dslab.slack.service.message.builder

import com.slack.api.model.block.LayoutBlock
import net.dslab.core.message.builder.MessageBuilder

interface SlackMessageBuilder : MessageBuilder<SlackMessageBuilder> {
    fun build(): List<LayoutBlock>
}
