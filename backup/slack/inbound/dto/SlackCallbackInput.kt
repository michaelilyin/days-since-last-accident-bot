package ilyin.slack.inbound.dto

import ilyin.slack.model.SlackCallbackType

class SlackCallbackInput(
    val type: SlackCallbackType
) : HashMap<String, Any>() {
}
