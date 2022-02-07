package net.dslab.slack.service.command

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SlackCommandResultBuilderFactoryImpl : SlackCommandResultBuilderFactory {
    override fun builder(): SlackCommandResultBuilder {
        return Builder()
    }

    class Builder : SlackCommandResultBuilder {
        private var text: String? = null

        override fun plainText(text: String): Builder {
            this.text = text
            return this
        }

        override fun build(): String {
            return text ?: ""
        }
    }
}
