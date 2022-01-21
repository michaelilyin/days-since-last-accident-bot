package ilyin.slack.service.auth

import ilyin.slack.service.auth.model.InitialTokenRequestResult

interface SlackInitialAuthService {
    fun performInitialTokenRequest(code: String): InitialTokenRequestResult
}
