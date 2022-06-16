package net.dslab.slack.dao

import com.google.cloud.firestore.Transaction
import net.dslab.slack.dao.model.TrackingSettingsEntity

interface TrackingSettingsDao {
    fun findSettings(t: Transaction, teamId: String, chatId: String): TrackingSettingsEntity?
    fun updateSettings(t: Transaction, teamId: String, chatId: String, value: TrackingSettingsEntity)
}
