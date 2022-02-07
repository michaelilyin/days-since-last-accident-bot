package net.dslab.slack.service.installation

import net.dslab.slack.service.auth.SlackInitialAuthenticator
import net.dslab.slack.service.auth.model.InitialTokenRequestResult
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
internal class SlackInstallationServiceImpl @Inject constructor(
    private val slackInitialAuthService: SlackInitialAuthenticator
) : SlackInstallationService {

    override fun processInstallCallback(code: String): InitialTokenRequestResult {
        return slackInitialAuthService.performInitialTokenRequest(code)
    }

}

