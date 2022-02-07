package net.dslab.slack.service.installation

import net.dslab.slack.service.auth.model.InitialTokenRequestResult

interface SlackInstallationService {
    fun processInstallCallback(code: String): InitialTokenRequestResult
}
