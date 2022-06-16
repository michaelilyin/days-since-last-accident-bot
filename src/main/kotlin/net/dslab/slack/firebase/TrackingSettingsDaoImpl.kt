package net.dslab.slack.firebase

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.SetOptions
import com.google.cloud.firestore.Transaction
import net.dslab.slack.dao.TrackingSettingsDao
import net.dslab.slack.dao.model.TrackingSettingsEntity
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class TrackingSettingsDaoImpl @Inject constructor(
    private val firestore: Firestore,
    private val objectMapper: ObjectMapper
) : TrackingSettingsDao {
    override fun findSettings(t: Transaction, teamId: String, chatId: String): TrackingSettingsEntity? {
        val ref = firestore
            .collection("slack-team")
            .document(teamId)
            .collection("settings")
            .document(chatId)
        val settingsDoc = t.get(ref)
            .get()

        if (!settingsDoc.exists()) {
            return null
        }

        return objectMapper.convertValue(settingsDoc.data, TrackingSettingsEntity::class.java)
    }

    override fun updateSettings(t: Transaction, teamId: String, chatId: String, value: TrackingSettingsEntity) {
        val ref = firestore
            .collection("slack-team")
            .document(teamId)
            .collection("settings")
            .document(chatId)
        t.set(ref, value, SetOptions.merge())
    }
}
