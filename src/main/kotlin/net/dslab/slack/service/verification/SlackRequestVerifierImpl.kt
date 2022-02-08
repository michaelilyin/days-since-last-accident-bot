package net.dslab.slack.service.verification

import mu.KLogger
import net.dslab.slack.api.http.filter.verification.SlackVerificationException
import net.dslab.slack.service.verification.properties.SlackVerificationProperties
import org.apache.commons.codec.binary.Hex
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
internal class SlackRequestVerifierImpl @Inject constructor(
    private val slackVerificationProperties: SlackVerificationProperties,
    private val logger: KLogger
) : SlackRequestVerifier {
    override fun verify(data: ByteArray, signature: String) {
        val hmacSha256: Mac = Mac.getInstance("HmacSHA256")
        val secret = slackVerificationProperties.secret().toByteArray()
        val key = SecretKeySpec(secret, "HmacSHA256")
        hmacSha256.init(key)
        val hashedBytes = hmacSha256.doFinal(data)
        val actualSignature = "v0=${Hex.encodeHexString(hashedBytes)}"

        if (signature != actualSignature) {
            // TODO: in dev mode log actual signature
            logger.error { "Actual signature [---] but $signature was provided" }
            throw SlackVerificationException(actualSignature, signature)
        }
    }
}
