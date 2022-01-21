package ilyin.slack.service.auth.dao

import ilyin.slack.service.auth.model.SlackAuthData

interface SlackAuthDao {
    fun storeAuthInfo(teamId: String, data: SlackAuthData)
    fun getAuthInfo(teamId: String): SlackAuthData?
}
