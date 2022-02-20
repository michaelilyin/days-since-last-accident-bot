package net.dslab.core.vendor

import net.dslab.core.vendor.model.TrackingSettings

interface TrackingSettingsService {
    fun findMainSettings(teamId: String, id: String): TrackingSettings
    fun enableTracking(teamId: String, id: String)
}
