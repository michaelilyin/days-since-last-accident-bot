package net.dslab.slack.service.message.builder

import com.slack.api.model.Message
import com.slack.api.model.block.Blocks
import com.slack.api.model.block.LayoutBlock
import net.dslab.core.message.builder.ParagraphBuilder
import net.dslab.slack.service.message.builder.paragraph.SlackParagraphBuilderFactory
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SlackMessageBuilderFactoryImpl @Inject constructor(
    private val paragraphBuilderFactory: SlackParagraphBuilderFactory
) : SlackMessageBuilderFactory {
    override fun builder(): SlackMessageBuilder {
        return Builder()
    }

    private inner class Builder : SlackMessageBuilder {
        private val blocks = mutableListOf<LayoutBlock>()

        override fun paragraph(configurer: ParagraphBuilder.() -> Unit) {
            val paragraphBuilder = paragraphBuilderFactory.builder()
            paragraphBuilder.configurer()
            blocks.add(paragraphBuilder.build())
        }

        override fun build(): List<LayoutBlock> {
            return blocks;
        }
    }
}
