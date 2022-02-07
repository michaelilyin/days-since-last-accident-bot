package net.dslab.slack.service.auth

import net.dslab.slack.service.auth.model.InitialTokenRequestResult

interface SlackInitialAuthenticator {
    fun performInitialTokenRequest(code: String): InitialTokenRequestResult
}
