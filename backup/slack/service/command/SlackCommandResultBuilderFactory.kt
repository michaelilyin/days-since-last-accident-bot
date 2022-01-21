package ilyin.slack.service.command

interface SlackCommandResultBuilderFactory {
    fun newBuilder(): SlackCommandResultBuilder
}
