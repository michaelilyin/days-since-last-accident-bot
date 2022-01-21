package ilyin.slack.service.command.impl

import ilyin.slack.service.command.SlackCommandResultBuilder
import ilyin.slack.service.command.SlackCommandResultBuilderFactory
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SlackCommandResultBuilderFactoryImpl : SlackCommandResultBuilderFactory {
    override fun newBuilder(): SlackCommandResultBuilder {
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
