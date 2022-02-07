package net.dslab.slack.firebase

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.SetOptions
import net.dslab.slack.dao.SlackTeamAuthDao
import net.dslab.slack.dao.model.SlackTeamAuth
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SlackTeamAuthDaoImpl @Inject constructor(
    private val firestore: Firestore,
    private val objectMapper: ObjectMapper
) : SlackTeamAuthDao {
    override fun storeAuthInfo(teamId: String, data: SlackTeamAuth) {
        firestore.collection("slack-team")
            .document(teamId)
            .collection("auth")
            .document("dslab")
            .set(data, SetOptions.merge())
            .get()
    }

    override fun getAuthInfo(teamId: String): SlackTeamAuth? {
        val document = firestore.collection("slack-team")
            .document(teamId)
            .collection("auth")
            .document("dslab")
            .get()
            .get()

        if (!document.exists()) {
            return null
        }

        return objectMapper.convertValue(document.data, SlackTeamAuth::class.java)
    }
}
