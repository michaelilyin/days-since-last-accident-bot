package net.dslab.core.vendor

import net.dslab.core.vendor.model.ChatInfo

interface ChatService {
    fun getChatInfo(teamId: String, chatId: String): ChatInfo?
    fun join(teamId: String, chatId: String)
}
