package net.dslab.slack.api.http.filter.verification

import io.vertx.core.http.HttpServerRequest
import mu.KLogger
import net.dslab.common.exception.BotException
import net.dslab.slack.service.verification.SlackRequestVerifier
import java.io.OutputStream
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import javax.ws.rs.ext.Provider
import javax.ws.rs.ext.ReaderInterceptor
import javax.ws.rs.ext.ReaderInterceptorContext
import javax.ws.rs.ext.WriterInterceptor
import javax.ws.rs.ext.WriterInterceptorContext


@Provider
@VerifySlackRequests
class SlackRequestVerificationInterceptor : ReaderInterceptor, WriterInterceptor {

    @Context
    internal lateinit var uriInfo: UriInfo

    @Context
    internal lateinit var request: HttpServerRequest

    @Inject
    internal lateinit var slackRequestVerifier: SlackRequestVerifier

    @Inject
    internal lateinit var logger: KLogger

    override fun aroundReadFrom(context: ReaderInterceptorContext): Any {
        logger.info { "Verify Slack request for ${request.method()}:${request.absoluteURI()}" }

        val signatureHeader = context.headers.getFirst("X-Slack-Signature")
            ?: throw BotException("Required verification header has not been provided")
        val timestampHeader = context.headers.getFirst("X-Slack-Request-Timestamp")
            ?: throw BotException("Required timestamp header has not been provided")

        val bodyBytes = context.inputStream.readAllBytes()
        val headerBytes = "v0:$timestampHeader:".toByteArray()

        slackRequestVerifier.verify(headerBytes + bodyBytes, signatureHeader)

        logger.info { "Slack request verified" }

        context.inputStream = bodyBytes.inputStream()

        logger.debug { "Request body ---${bodyBytes.toString(Charsets.UTF_8)}---" }

        return context.proceed()
    }

    override fun aroundWriteTo(context: WriterInterceptorContext) {
        context.outputStream = LoggingOutputStream(context.outputStream)
        context.proceed()
    }

    private inner class LoggingOutputStream(
        private val delegate: OutputStream
    ) : OutputStream() {
        private val data = mutableListOf<Byte>()

        override fun write(b: Int) {
            data.add(b.toByte())
            delegate.write(b)
        }

        override fun close() {
            try {
                logger.debug { "Response body ---${String(data.toByteArray(), Charsets.UTF_8)}---" }
            } catch (e: Exception) {
                logger.warn(e) { "Cannot log response body" }
            }
            delegate.close()
        }

        override fun flush() {
            delegate.flush()
        }
    }
}
