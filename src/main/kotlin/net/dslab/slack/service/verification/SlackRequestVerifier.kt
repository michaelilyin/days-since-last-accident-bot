package net.dslab.slack.service.verification

interface SlackRequestVerifier {
    fun verify(data: ByteArray, signature: String)
}
