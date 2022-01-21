package ilyin.slack.dao.impl.firestore

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.SetOptions
import ilyin.slack.service.auth.dao.SlackAuthDao
import ilyin.slack.service.auth.model.SlackAuthData
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SlackAuthDaoImpl(
    private val firestore: Firestore,
    private val objectMapper: ObjectMapper
) : SlackAuthDao {
    override fun storeAuthInfo(teamId: String, data: SlackAuthData) {
        firestore.collection("slack-team")
            .document(teamId)
            .collection("auth")
            .document("days-since-last-accident")
            .set(data, SetOptions.merge())
            .get()
    }

    override fun getAuthInfo(teamId: String): SlackAuthData? {
        val document = firestore.collection("slack-team")
            .document(teamId)
            .collection("auth")
            .document("days-since-last-accident")
            .get()
            .get()

        if (!document.exists()) {
            return null
        }

        return objectMapper.convertValue(document.data, SlackAuthData::class.java)
    }
}
