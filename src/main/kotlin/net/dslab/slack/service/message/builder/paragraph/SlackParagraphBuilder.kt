package net.dslab.slack.service.message.builder.paragraph

import com.slack.api.model.block.SectionBlock
import net.dslab.core.message.builder.ParagraphBuilder

interface SlackParagraphBuilder : ParagraphBuilder {
    fun build(): SectionBlock
}
