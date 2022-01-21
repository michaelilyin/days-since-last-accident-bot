package ilyin.slack.service.installation

import ilyin.slack.service.auth.model.InitialTokenRequestResult

interface SlackInstallationService {
    fun processInstallCallback(code: String): InitialTokenRequestResult
}
