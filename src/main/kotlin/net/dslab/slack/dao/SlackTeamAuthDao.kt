package net.dslab.slack.dao

import com.google.cloud.firestore.Transaction
import net.dslab.slack.dao.model.SlackTeamAuthEntity

interface SlackTeamAuthDao {
    fun storeAuthInfo(t: Transaction, teamId: String, data: SlackTeamAuthEntity)
    fun getAuthInfo(t: Transaction, teamId: String): SlackTeamAuthEntity?
}
