package net.dslab.slack.service.auth

interface SlackAuthTokenRegistry {
    fun getAuthToken(teamId: String): String
}
