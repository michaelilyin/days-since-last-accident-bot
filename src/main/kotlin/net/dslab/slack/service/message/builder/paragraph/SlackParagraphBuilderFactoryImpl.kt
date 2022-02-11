package net.dslab.slack.service.message.builder.paragraph

import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.composition.PlainTextObject
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SlackParagraphBuilderFactoryImpl : SlackParagraphBuilderFactory {
    override fun builder(): SlackParagraphBuilder {
        return Builder()
    }

    private class Builder : SlackParagraphBuilder {
        private val sectionBuilder: SectionBlock.SectionBlockBuilder = SectionBlock.builder();

        override fun plainText(text: String) {
            val textObject = PlainTextObject.builder()
                .text(text)
                .build()
            sectionBuilder.text(textObject)
        }

        override fun build(): SectionBlock {
            return sectionBuilder.build()
        }
    }
}
