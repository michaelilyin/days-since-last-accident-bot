package net.dslab.slack.service.vendor.model

import com.slack.api.model.Conversation
import net.dslab.core.vendor.model.ChatInfo

data class SlackChatInfo(
    override val id: String,
    override val name: String,
    override val member: Boolean
) : ChatInfo {
    constructor(conversation: Conversation): this(
        id = conversation.id,
        name = conversation.name,
        member = conversation.isMember
    )
}
