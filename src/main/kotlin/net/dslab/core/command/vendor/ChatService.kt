package net.dslab.core.command.vendor

import net.dslab.core.command.vendor.model.ChatInfo

interface ChatService {
    fun getChatInfo(teamId: String, chatId: String): ChatInfo?
    fun join(teamId: String, chatId: String)
}
