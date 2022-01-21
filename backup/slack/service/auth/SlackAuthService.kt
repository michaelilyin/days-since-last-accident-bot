package ilyin.slack.service.auth

interface SlackAuthService {
    fun getAuthToken(teamId: String): String
}
