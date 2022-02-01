package net.dslab.slack.api.http.model

import net.dslab.slack.model.SlackCallbackType

class SlackCallbackInput(
    val type: SlackCallbackType
) : HashMap<String, Any>() {
}
