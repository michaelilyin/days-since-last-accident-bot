package net.dslab.slack.service.vendor

import net.dslab.core.vendor.TrackingSettingsService
import net.dslab.core.vendor.model.TrackingSettings
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TrackingSettingsServiceImpl : TrackingSettingsService {
    override fun findMainSettings(teamId: String, id: String): TrackingSettings {
        TODO("Not yet implemented")
    }

    override fun enableTracking(teamId: String, id: String) {
        TODO("Not yet implemented")
    }
}
