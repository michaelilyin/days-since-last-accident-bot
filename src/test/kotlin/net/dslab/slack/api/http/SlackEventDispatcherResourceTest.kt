package net.dslab.slack.api.http

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import net.dslab.slack.api.http.filter.verification.SlackRequestVerificationInterceptor
import net.dslab.slack.api.http.filter.verification.VerifySlackRequests
import net.dslab.slack.service.verification.SlackRequestVerifier
import net.dslab.slackHttpResource
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

@QuarkusTest
@TestHTTPEndpoint(SlackEventDispatcherResource::class)
internal class SlackEventDispatcherResourceTest {

    @InjectMock
    private lateinit var slackRequestVerifier: SlackRequestVerifier

    @Test
    internal fun urlVerificationOK() {
        given()
            .`when`()
            .header("X-Slack-Signature", "signatire")
            .header("X-Slack-Request-Timestamp", "timestamp")
            .contentType(ContentType.JSON)
            .body(slackHttpResource("url_verification_callback.json"))
            .post("event")
            .then()
            .statusCode(200)
            .body("challenge", Matchers.equalTo("challenge-value"))
    }

    @Test
    internal fun eventCallbackOK() {
        given()
            .`when`()
            .header("X-Slack-Signature", "signatire")
            .header("X-Slack-Request-Timestamp", "timestamp")
            .contentType(ContentType.JSON)
            .body(slackHttpResource("event_callback.json"))
            .post("event")
            .then()
            .statusCode(200)
    }
}
