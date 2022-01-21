package ilyin.slack.service.installation

import ilyin.slack.service.auth.SlackInitialAuthService
import ilyin.slack.service.auth.model.InitialTokenRequestResult
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SlackInstallationServiceImpl(
    private val slackInitialAuthService: SlackInitialAuthService
) : SlackInstallationService {

    override fun processInstallCallback(code: String): InitialTokenRequestResult {
        return slackInitialAuthService.performInitialTokenRequest(code)
    }

}
