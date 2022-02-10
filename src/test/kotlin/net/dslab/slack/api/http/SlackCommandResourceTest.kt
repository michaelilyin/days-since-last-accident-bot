package net.dslab.slack.api.http

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.restassured.RestAssured
import net.dslab.slack.service.command.SlackCommandExecutionService
import net.dslab.slack.service.verification.SlackRequestVerifier
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.kotlin.argThat

@QuarkusTest
@TestHTTPEndpoint(SlackCommandResource::class)
internal class SlackCommandResourceTest {
    @InjectMock
    private lateinit var slackCommandExecutionService: SlackCommandExecutionService

    @InjectMock
    private lateinit var slackRequestVerifier: SlackRequestVerifier

    @Test
    internal fun enableTrackingOK() {
        RestAssured
            .given()
            .header("X-Slack-Signature", "signatire")
            .header("X-Slack-Request-Timestamp", "timestamp")
            .formParam("token", "token-value")
            .formParam("team_id", "team-id-value")
            .formParam("team_domain", "team-domain-value")
            .formParam("channel_id", "channel-id-value")
            .formParam("channel_name", "channel-name-value")
            .formParam("user_id", "user-id-value")
            .formParam("user_name", "user-name-value")
            .formParam("command", "/enable-days-since-counter")
            .formParam("response_url", "https://localhost")
            .formParam("trigger_id", "trigger-id-value")
            .post("slash")
            .then()
            .statusCode(204)

        BDDMockito.verify(slackCommandExecutionService)
            .run(argThat { command == "/enable-days-since-counter" })
    }
}
