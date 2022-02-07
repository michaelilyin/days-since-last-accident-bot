package net.dslab.slack.dao

import net.dslab.slack.dao.model.SlackTeamAuth

interface SlackTeamAuthDao {
    fun storeAuthInfo(teamId: String, data: SlackTeamAuth)
    fun getAuthInfo(teamId: String): SlackTeamAuth?
}
