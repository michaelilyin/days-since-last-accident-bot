package ilyin.slack.inbound.filter

import ilyin.slack.exception.SlackVerificationException
import io.vertx.core.http.HttpServerRequest
import mu.KLogger
import org.apache.commons.codec.binary.Hex
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import javax.ws.rs.ext.Provider
import javax.ws.rs.ext.ReaderInterceptor
import javax.ws.rs.ext.ReaderInterceptorContext


//@Provider
@VerifySlackRequests
class SlackRequestVerificationInterceptor : ReaderInterceptor {

    @Context
    lateinit var uriInfo: UriInfo

    @Context
    lateinit var request: HttpServerRequest

    @ConfigProperty(name = "slack.verification.secret")
    lateinit var secret: String

    @Inject
    lateinit var logger: KLogger

    override fun aroundReadFrom(context: ReaderInterceptorContext): Any {
        logger.info { "Verify Slack request" }
        val signatureHeader = context.headers.getFirst("X-Slack-Signature")
            ?: throw SlackVerificationException("Required verification header has not been provided")
        val timestampHeader = context.headers.getFirst("X-Slack-Request-Timestamp")
            ?: throw SlackVerificationException("Required timestamp header has not been provided")

        val bodyBytes = context.inputStream.readAllBytes()
        val headerBytes = "v0:$timestampHeader:".toByteArray()

        val hmacSha256: Mac = Mac.getInstance("HmacSHA256")
        val secret = secret.toByteArray()
        val key = SecretKeySpec(secret, "HmacSHA256")
        hmacSha256.init(key)
        val hashedBytes = hmacSha256.doFinal(headerBytes + bodyBytes)
        val actualSignature = "v0=${Hex.encodeHexString(hashedBytes)}"

        if (signatureHeader != actualSignature) {
            logger.error { "Actual signature $actualSignature but $signatureHeader was provided" }
            throw SlackVerificationException("Actual signature $actualSignature but $signatureHeader was provided")
        }

        context.inputStream = bodyBytes.inputStream()
        return context.proceed()
    }
}
