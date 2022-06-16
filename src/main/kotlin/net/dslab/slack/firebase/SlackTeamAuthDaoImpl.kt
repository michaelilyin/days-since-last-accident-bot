package net.dslab.slack.firebase

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.SetOptions
import com.google.cloud.firestore.Transaction
import net.dslab.slack.dao.SlackTeamAuthDao
import net.dslab.slack.dao.model.SlackTeamAuthEntity
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SlackTeamAuthDaoImpl @Inject constructor(
    private val firestore: Firestore,
    private val objectMapper: ObjectMapper
) : SlackTeamAuthDao {
    override fun storeAuthInfo(t: Transaction, teamId: String, data: SlackTeamAuthEntity) {
        val ref = firestore.collection("slack-team")
            .document(teamId)
            .collection("auth")
            .document("dslab")
        t.set(ref, data, SetOptions.merge())
    }

    override fun getAuthInfo(t: Transaction, teamId: String): SlackTeamAuthEntity? {
        val ref = firestore.collection("slack-team")
            .document(teamId)
            .collection("auth")
            .document("dslab")

        val document = t.get(ref).get()

        if (!document.exists()) {
            return null
        }

        return objectMapper.convertValue(document.data, SlackTeamAuthEntity::class.java)
    }
}
