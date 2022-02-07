package net.dslab.slack.service.auth.model

data class InitialTokenRequestResult(
    val teamId: String,
    val botUserId: String
) {
}
