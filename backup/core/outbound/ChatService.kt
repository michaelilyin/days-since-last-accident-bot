package ilyin.core.outbound

import ilyin.core.outbound.model.ChatInfo

interface ChatService {
    fun getChatInfo(teamId: String, chatId: String): ChatInfo?
    fun join(teamId: String, chatId: String)
}
