package net.dslab.slack.api.http.filter.verification

import io.vertx.core.http.HttpServerRequest
import mu.KLogger
import net.dslab.common.exception.BotException
import net.dslab.slack.service.verification.SlackRequestVerifier
import javax.inject.Inject
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import javax.ws.rs.ext.Provider
import javax.ws.rs.ext.ReaderInterceptor
import javax.ws.rs.ext.ReaderInterceptorContext


@Provider
@VerifySlackRequests
class SlackRequestVerificationInterceptor : ReaderInterceptor {

    @Context
    private lateinit var uriInfo: UriInfo

    @Context
    private lateinit var request: HttpServerRequest

    @Inject
    private lateinit var slackRequestVerifier: SlackRequestVerifier

    override fun aroundReadFrom(context: ReaderInterceptorContext): Any {
        val signatureHeader = context.headers.getFirst("X-Slack-Signature")
            ?: throw BotException("Required verification header has not been provided")
        val timestampHeader = context.headers.getFirst("X-Slack-Request-Timestamp")
            ?: throw BotException("Required timestamp header has not been provided")

        val bodyBytes = context.inputStream.readAllBytes()
        val headerBytes = "v0:$timestampHeader:".toByteArray()

        slackRequestVerifier.verify(headerBytes + bodyBytes, signatureHeader)

        context.inputStream = bodyBytes.inputStream()
        return context.proceed()
    }
}
